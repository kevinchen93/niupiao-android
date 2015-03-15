package com.niupiao.niupiao.fragments.payment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.niupiao.niupiao.R;
import com.niupiao.niupiao.managers.PaymentManager;
import com.niupiao.niupiao.models.Ticket;
import com.niupiao.niupiao.models.TicketStatus;

import java.util.Collection;
import java.util.Map;

/**
 * Created by kevinchen on 3/9/15.
 */
public class TransferTicketsFragment extends CheckoutViewPagerFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_payment_transfer, container, false);

        // Tickets and payment info are stored in the PaymentManager
        PaymentManager paymentManager = getPaymentManager();

        // Set the total amount of money spent so far
        TextView cost = (TextView) root.findViewById(R.id.tv_cost);
        cost.setText("$" + paymentManager.getTotalCost());

        // Maps ticketStatus to a collection of tickets the size of ticketStatus.getMaxPurchasable()
        Map<TicketStatus, Collection<Ticket>> tickets = paymentManager.getTickets();

        // We will be adding stuff to the sole child of a ScrollView. (ScrollView can only have one child).
        RelativeLayout insideScrollView = (RelativeLayout) root.findViewById(R.id.sv_child);

        // The recipient number starts at 1
        int recipientNumber = 1;

        // We will be adding a group of rows for each ticket status
        for (TicketStatus ticketStatus : tickets.keySet()) {

            // The max number of tickets you can buy for any given status is the number of rows we show
            int numRowsToAdd = ticketStatus.getMaxPurchasable();

            // The layout inflater will be dynamically inflating views,
            LayoutInflater layoutInflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            // Add a row for each ticket the user can potentially buy
            for (int i = 0; i < numRowsToAdd; i++) {

                // Inflate the row that shows the recipient number, the ticket status, and the choose recipient button
                // See payment_transfer_recipient_row.xml
                View child = layoutInflater.inflate(R.layout.payment_transfer_recipient_row, insideScrollView);

                // Show the recipient number
                TextView recipientNumberTextView = (TextView) child.findViewById(R.id.tv_recipient_number);
                recipientNumberTextView.setText("" + recipientNumber);
                recipientNumber++;

                // Show the ticket status
                TextView statusTextView = (TextView) child.findViewById(R.id.tv_status);
                statusTextView.setText(ticketStatus.getName());
                insideScrollView.addView(child, recipientNumber);

                // Set the click listener for the Recipient Chooser button
                ImageButton chooseContactImageButton = (ImageButton) child.findViewById(R.id.ib_choose_contact);
                chooseContactImageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // TODO show Contact Chooser dialog/fragment
                        Toast.makeText(getActivity(), "TODO - show contact chooser", Toast.LENGTH_LONG).show();
                    }
                });
            }
        }

        // Show the button that will lead to the next screen
        ImageButton next = (ImageButton) root.findViewById(R.id.ib_next_screen);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextCheckoutPhase();
            }
        });
        next.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.payment_method_arrow));

        return root;
    }

    @Override
    public String getTitle() {
        return "Transfer Tickets";
    }

    public static TransferTicketsFragment newInstance() {
        return new TransferTicketsFragment();
    }
}
