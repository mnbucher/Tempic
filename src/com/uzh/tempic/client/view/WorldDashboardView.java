package com.uzh.tempic.client.view;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.user.client.ui.*;
import com.uzh.tempic.client.presenter.WorldDashboardPresenter;

import java.util.ArrayList;
import java.util.List;

public class WorldDashboardView extends Composite implements WorldDashboardPresenter.Display {
    private final Button addButton;
    private final Button deleteButton;
    private FlexTable dashboardTable;
    private final FlexTable contentTable;

    public WorldDashboardView() {
        DecoratorPanel contentTableDecorator = new DecoratorPanel();
        initWidget(contentTableDecorator);
        contentTableDecorator.setWidth("100%");
        contentTableDecorator.setWidth("18em");

        contentTable = new FlexTable();
        contentTable.setWidth("100%");
        contentTable.getCellFormatter().addStyleName(0, 0, "worldDashboard-ListContainer");
        contentTable.getCellFormatter().setWidth(0, 0, "100%");
        contentTable.getFlexCellFormatter().setVerticalAlignment(0, 0, DockPanel.ALIGN_TOP);

        // Create the menu
        //
        HorizontalPanel hPanel = new HorizontalPanel();
        hPanel.setBorderWidth(0);
        hPanel.setSpacing(0);
        hPanel.setHorizontalAlignment(HorizontalPanel.ALIGN_LEFT);
        addButton = new Button("Add");
        hPanel.add(addButton);
        deleteButton = new Button("Delete");
        hPanel.add(deleteButton);
        contentTable.getCellFormatter().addStyleName(0, 0, "worldDashboard-ListMenu");
        contentTable.setWidget(0, 0, hPanel);

        // Create the contacts list
        //
        dashboardTable = new FlexTable();
        dashboardTable.setCellSpacing(0);
        dashboardTable.setCellPadding(0);
        dashboardTable.setWidth("100%");
        dashboardTable.addStyleName("worldDashboard-ListContents");
        dashboardTable.getColumnFormatter().setWidth(0, "15px");
        contentTable.setWidget(1, 0, dashboardTable);

        contentTableDecorator.add(contentTable);
    }

    public HasClickHandlers getAddButton() {
        return addButton;
    }

    public HasClickHandlers getDeleteButton() {
        return deleteButton;
    }

    public HasClickHandlers getList() {
        return dashboardTable;
    }

    public void setData(List<String> data) {
        dashboardTable.removeAllRows();

        for (int i = 0; i < data.size(); ++i) {
            dashboardTable.setWidget(i, 0, new CheckBox());
            dashboardTable.setText(i, 1, data.get(i));
        }
    }

    public int getClickedRow(ClickEvent event) {
        int selectedRow = -1;
        HTMLTable.Cell cell = dashboardTable.getCellForEvent(event);

        if (cell != null) {
            // Suppress clicks if the user is actually selecting the
            //  check box
            //
            if (cell.getCellIndex() > 0) {
                selectedRow = cell.getRowIndex();
            }
        }

        return selectedRow;
    }

    public List<Integer> getSelectedRows() {
        List<Integer> selectedRows = new ArrayList<Integer>();

        for (int i = 0; i < dashboardTable.getRowCount(); ++i) {
            CheckBox checkBox = (CheckBox)dashboardTable.getWidget(i, 0);
            if (checkBox.getValue()) {
                selectedRows.add(i);
            }
        }

        return selectedRows;
    }

    public Widget asWidget() {
        return this;
    }

}
