package lalucia.babyhouse.babyhouse;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 *
 */
public class FragHome extends Fragment {

    private View view;
    private TextView homePageDetailstv;
    public FragHome() {
        // Required empty public constructor
    }
    //******************************************************************************
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_frag_home, container, false);
        homePageDetailstv = (TextView) view.findViewById(R.id.tvHomeData);
        FactoryClass objFactory = new FactoryClass(getActivity());
        assert homePageDetailstv != null;
        homePageDetailstv.setText(objFactory.readDate("homepage"));
        return view;
    }
}
