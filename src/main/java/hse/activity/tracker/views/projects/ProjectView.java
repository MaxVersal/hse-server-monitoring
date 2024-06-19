package hse.activity.tracker.views.projects;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import com.vaadin.flow.theme.lumo.LumoUtility;
import hse.activity.tracker.data.projects.Project;
import hse.activity.tracker.data.users.SamplePerson;
import hse.activity.tracker.services.ProjectService;
import hse.activity.tracker.services.SamplePersonService;
import hse.activity.tracker.views.AbstractGridView;
import hse.activity.tracker.views.MainLayout;
import jakarta.annotation.security.RolesAllowed;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@RolesAllowed("ADMIN")
@PageTitle("Projects")
@Route(value = "projects", layout = MainLayout.class)
@RouteAlias(value = "projects", layout = MainLayout.class)
@Uses(Icon.class)
public class ProjectView extends AbstractGridView<Project> {

    private final Filters filters;
    private final ProjectService projectService;
    private final SamplePersonService samplePersonService;

    public ProjectView(ProjectService projectService, SamplePersonService samplePersonService) {
        this.samplePersonService = samplePersonService;
        this.projectService = projectService;
        setSizeFull();
        addClassNames("users-view");

        filters = new Filters(this::refreshGrid, samplePersonService, projectService);
        VerticalLayout layout = new VerticalLayout(createMobileFilters(), filters, createGrid());
        layout.setSizeFull();
        layout.setPadding(false);
        layout.setSpacing(false);
        add(layout);
    }

    @Override
    protected Component createGrid() {
        grid = new Grid<>(Project.class, false);
        grid.addColumn("projectName").setAutoWidth(true);
        grid.addColumn(this::usersEmailFormat).setHeader("Users").setAutoWidth(true);

        grid.setItems(query -> projectService.list(
            PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query)),
            filters).stream());
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.addClassNames(LumoUtility.Border.TOP, LumoUtility.BorderColor.CONTRAST_10);

        return grid;
    }

    private String usersEmailFormat(Project project) {
        StringBuilder res = new StringBuilder();
        for (SamplePerson user : project.getUsers()) {
            res.append(user.getEmail()).append(",");
        }
        return res.substring(0, res.length() - 1);
    }

    private HorizontalLayout createMobileFilters() {
        // Mobile version
        HorizontalLayout mobileFilters = new HorizontalLayout();
        mobileFilters.setWidthFull();
        mobileFilters.addClassNames(LumoUtility.Padding.MEDIUM, LumoUtility.BoxSizing.BORDER,
            LumoUtility.AlignItems.CENTER);
        mobileFilters.addClassName("mobile-filters");

        Icon mobileIcon = new Icon("lumo", "plus");
        Span filtersHeading = new Span("Filters");
        mobileFilters.add(mobileIcon, filtersHeading);
        mobileFilters.setFlexGrow(1, filtersHeading);
        mobileFilters.addClickListener(e -> {
            if (filters.getClassNames().contains("visible")) {
                filters.removeClassName("visible");
                mobileIcon.getElement().setAttribute("icon", "lumo:plus");
            } else {
                filters.addClassName("visible");
                mobileIcon.getElement().setAttribute("icon", "lumo:minus");
            }
        });
        return mobileFilters;
    }

    public static class Filters extends Div implements Specification<Project> {

        private final TextField name = new TextField("Name");
        private final ProjectService projectService;
        private final SamplePersonService samplePersonService;

        private final Runnable onSearch;

        public Filters(Runnable onSearch, SamplePersonService samplePersonService, ProjectService projectService) {
            this.projectService = projectService;
            this.samplePersonService = samplePersonService;
            this.onSearch = onSearch;
            setWidthFull();
            addClassName("filter-layout");
            addClassNames(LumoUtility.Padding.Horizontal.LARGE, LumoUtility.Padding.Vertical.MEDIUM,
                LumoUtility.BoxSizing.BORDER);
            name.setPlaceholder("Project name");

            Button searchBtn = new Button("Search");
            searchBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            searchBtn.addClickListener(e -> onSearch.run());

            Button addUserButton = new Button("Add user");
            addUserButton.addClickListener(e -> {
                addUserDialog().open();
            });
            Button addProjectButton = new Button("Add project");

            Div actions = new Div(addProjectButton, addUserButton, searchBtn);
            actions.addClassName(LumoUtility.Gap.SMALL);
            actions.addClassName("actions");

            add(name, actions);
        }

        private ConfirmDialog addUserDialog() {
            ConfirmDialog dialog = new ConfirmDialog();
            dialog.setCancelable(true);
            Select<String> userTextField = new Select<>();
            List<String> names = samplePersonService.list()
                .stream()
                .map(SamplePerson::getEmail)
                .toList();
            userTextField.setItems(names);
            userTextField.setValue(names.get(0));
            Select<String> projectTextField = new Select<>();
            List<String> projects = projectService.findAll()
                .stream()
                .map(Project::getProjectName)
                .toList();
            projectTextField.setItems(projects);
            dialog.addConfirmListener(action -> {
                Long projectId = projectService.findByName(projectTextField.getValue()).getId();
                Long userId = samplePersonService.getByEmail(userTextField.getValue()).getId();
                projectService.addPersonToProject(projectId, userId);
                onSearch.run();
            });
            dialog.add(new HorizontalLayout(userTextField, projectTextField));
            return dialog;
        }

        @Override
        public Predicate toPredicate(Root<Project> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
            List<Predicate> predicates = new ArrayList<>();

            if (!name.isEmpty()) {
                String lowerCaseFilter = name.getValue().toLowerCase();
                Predicate firstNameMatch = criteriaBuilder.like(criteriaBuilder.lower(root.get("projectName")),
                    lowerCaseFilter + "%");

                predicates.add(criteriaBuilder.or(firstNameMatch));
            }

            return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
        }
    }
}
