package lalucia.babyhouse.babyhouse;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragDonate extends Fragment {

    public FragDonate() {
        // Required empty public constructor
    }
    //*************************************************************************
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_frag_donate, container, false);
        TextView donationDetailstv = (TextView) view.findViewById(R.id.tvDonation);
        FactoryClass objFactory = new FactoryClass(getActivity());
        donationDetailstv.setText(objFactory.readDate("donations"));
        return view;
    }
}
