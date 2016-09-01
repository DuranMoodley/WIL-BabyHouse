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
public class FragEvents extends Fragment
{
    private View view;
    private TextView eventstv;
    //*********************************************************
    public FragEvents()
    {
        // Required empty public constructor
    }
    //*********************************************************
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_frag_events, container, false);
        eventstv = (TextView) view.findViewById(R.id.tvEvents);
        return view;
    }
}
