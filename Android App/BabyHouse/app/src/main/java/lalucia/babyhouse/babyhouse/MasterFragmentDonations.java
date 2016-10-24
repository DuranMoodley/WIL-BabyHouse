/*
MasterFragmentDonations.java
Shows a list of donations options that the user can select, seleting an option will open information
Lecturer : Rajesh Chanderman
WIL Assessment
Date Updated : 10/24/16
 */
package lalucia.babyhouse.babyhouse;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;


/**
 * A simple {@link Fragment} subclass.
 */
public class MasterFragmentDonations extends ListFragment {

    private String [] names;
    public MasterFragmentDonations() {
        // Required empty public constructor
    }
    //**************************************************************************
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Shows a list item containg the array elements
        names = new String[]{"EFT", "ZAPPER"};
        setListAdapter(new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,names));
    }
    //**************************************************************************
    @Override
    public void onListItemClick(ListView l, View v, int position, long id)
    {
        //Fragment containing details about each options is opened, information is dependant on user selection
        FragDetailedDonations fragDetailedDonations = (FragDetailedDonations) getFragmentManager().findFragmentById(R.id.detailedFragment);
        String selectedItem = names[position];

        //Select item is passed through to fragment class
        if(fragDetailedDonations != null && fragDetailedDonations.isInLayout())
        {
            fragDetailedDonations.getSelectedItem(selectedItem);
        }
        else
        {
           Intent intent = new Intent(getActivity(),DonationDetail.class);
           intent.putExtra("name",selectedItem);
            startActivity(intent);
        }
    }
}
