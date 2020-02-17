package com.raukhvarger.ms.webfs.view;

import com.raukhvarger.ms.webfs.entity.Person;
import com.raukhvarger.ms.webfs.service.PersonService;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.dnd.GridDropMode;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.treegrid.TreeGrid;
import com.vaadin.flow.data.provider.hierarchy.TreeData;
import com.vaadin.flow.data.provider.hierarchy.TreeDataProvider;
import com.vaadin.flow.router.PreserveOnRefresh;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@Route
@PreserveOnRefresh
public class MainView extends VerticalLayout {

	@Autowired
	private PersonService personService;

	@PostConstruct
	public void init() {
		add(new Text("Welcome to MainView."));


		final Person[] draggedItem = {new Person()};

		TreeGrid<Person> grid = new TreeGrid<>();
		add(grid);

		TreeData<Person> td = new TreeData<>();

// Disallow dragging supervisors
		grid.setDragFilter(person -> td.getParent(person) != null);

		grid.setDropFilter(person ->
// Only support dropping on top of supervisors
				td.getRootItems().contains(person)
						// Don't allow more than 4 subordinates
						&& td.getChildren(person).size() < 4
						// Disallow dropping on own supervisor
						&& !td.getChildren(person).contains(draggedItem[0]));

		grid.addHierarchyColumn(Person::getFirstName).setHeader("First name");
		grid.addColumn(Person::getLastName).setHeader("Last name");
		grid.addColumn(Person::getPhoneNumber).setHeader("Phone");
		grid.setRowsDraggable(true);
		grid.setSelectionMode(Grid.SelectionMode.NONE);

		td.addItems(null, personService.fetch(0, 3));
		td.addItems(td.getRootItems().get(0), personService.fetch(3, 3));
		td.addItems(td.getRootItems().get(1), personService.fetch(6, 2));

		grid.setDataProvider(new TreeDataProvider<Person>(td));
		grid.expand(td.getRootItems());

		grid.addDragStartListener(event -> {
			draggedItem[0] = event.getDraggedItems().get(0);
			grid.setDropMode(GridDropMode.ON_TOP);

			// Refresh all related items to get the drop filter run for them
			//
			// For flat grids, dataProvider.refreshAll() does the job well but
			// for a TreeGrid with nodes expanded, it's more efficient to
			// refresh the items individually
			td.getRootItems().forEach(supervisor -> {
				grid.getDataProvider().refreshItem(supervisor);

				td.getChildren(supervisor).forEach(subordinate -> grid
						.getDataProvider().refreshItem(subordinate));
			});

		});

		grid.addDragEndListener(event -> {
			draggedItem[0] = null;
			grid.setDropMode(null);
		});

		grid.addDropListener(event -> {
			event.getDropTargetItem().ifPresent(supervisor -> {
				// Remove the item from it's previous supervisor's subordinates
				// list
				td.removeItem(draggedItem[0]);

				// Close empty parents
				td.getRootItems().forEach(root -> {
					if (td.getChildren(root).isEmpty()) {
						grid.collapse(root);
					}
				});

				// Add the item to the target supervisor's subordinates list
				td.addItem(supervisor, draggedItem[0]);

				grid.getDataProvider().refreshAll();
			});
		});
	}

}