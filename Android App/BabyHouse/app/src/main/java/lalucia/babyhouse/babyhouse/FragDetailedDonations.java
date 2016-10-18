package lalucia.babyhouse.babyhouse;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragDetailedDonations extends Fragment {

    TextView donationDetailes;
    private View view;
    private ImageView imgzap;
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
    //*********************************************************************
    public FragDetailedDonations() {
        // Required empty public constructor
    }
    //*********************************************************************
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_frag_detailed_donations, container, false);
        donationDetailes = (TextView) view.findViewById(R.id.tvNames);
        imgzap = (ImageView) view.findViewById(R.id.imgZap);
        return view;
    }
    //*********************************************************************
    public void getSelectedItem(String name)
    {
        if(name.equalsIgnoreCase("ZAPPER"))
        {
            showZapper();
        }
        else
        {
            showEft();
        }
    }
    //*********************************************************************
    public void showZapper()
    {
        imgzap.setVisibility(View.VISIBLE);
        donationDetailes.setVisibility(View.INVISIBLE);
    }
    //********************************************************************************
    public void showEft()
    {
        imgzap.setVisibility(View.INVISIBLE);
        donationDetailes.setVisibility(View.VISIBLE);
        FactoryClass objFactory = new FactoryClass(getActivity());
        String donationText = objFactory.readDate("donations");
        donationDetailes.setText(donationText);
    }
}
