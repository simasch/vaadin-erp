package io.seventytwo.erp.view;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import io.seventytwo.db.tables.records.CustomerRecord;
import org.jooq.DSLContext;

import java.util.List;

import static io.seventytwo.db.tables.Customer.CUSTOMER;

@PageTitle("ERP")
@Route("customers")
public class CustomersView extends VerticalLayout {

    private final DSLContext context;

    public CustomersView(DSLContext context) {
        this.context = context;

        add(new H1("Customers"));

        Grid<CustomerRecord> grid = new Grid<>();
        grid.addColumn(CustomerRecord::getId).setHeader("ID");
        grid.addColumn(CustomerRecord::getFirstName).setHeader("First Name");
        grid.addColumn(CustomerRecord::getLastName).setHeader("Last Name");
        grid.addColumn(CustomerRecord::getEmail).setHeader("E-Mail");
        grid.addColumn(new ComponentRenderer<>(customer ->
                new RouterLink("Edit", CustomerView.class, customer.getId())));

        List<CustomerRecord> customers = context.selectFrom(CUSTOMER).fetch();
        grid.setItems(customers);

        add(grid);

        add(new RouterLink("Back", IndexView.class));
    }
}