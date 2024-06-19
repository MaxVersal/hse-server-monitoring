package hse.activity.tracker.views;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;

/**
 * todo Document type AbstractGridView
 */
public abstract class AbstractGridView<T> extends Div {
    protected Grid<T> grid;

    protected void refreshGrid() {
        grid.getDataProvider().refreshAll();
    }

    protected abstract Component createGrid();
}
