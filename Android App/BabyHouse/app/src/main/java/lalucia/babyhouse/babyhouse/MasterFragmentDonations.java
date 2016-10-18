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

    String [] names;
    public MasterFragmentDonations() {
        // Required empty public constructor
    }
    //**************************************************************************
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        names = new String[]{"EFT", "ZAPPER"};
        setListAdapter(new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,names));
    }
    //**************************************************************************
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        FragDetailedDonations fragDetailedDonations = (FragDetailedDonations) getFragmentManager().findFragmentById(R.id.detailedFragment);
        String selectedItem = names[position];

        if(fragDetailedDonations != null && fragDetailedDonations.isInLayout())
        {
            fragDetailedDonations.getSelectedItem(selectedItem);
        }
        else
        {
           Intent intent = new Intent(getActivity(),DonationDetail.class);
           intent.putExtra("name",selectedItem);
            startActivity(intent);
            //Toast.makeText(getActivity(),"Detailed",Toast.LENGTH_LONG).show();
        }
    }
}
