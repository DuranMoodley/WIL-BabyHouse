package lalucia.babyhouse.babyhouse;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationDrawer extends Fragment implements MenuAdapter.RecycleClickListener{

    private RecyclerView recyclerView;
    private ActionBarDrawerToggle toggleDrawer;
    private MenuAdapter adapter;
    View container;
    private final String PREF_FILE_NAME = "preferenceDrawerData";
    private final String KEY_DRAWER_LAYOUT = "drawer_exists";
    private DrawerLayout drawerLayout;
    private boolean drawerExisted;
    private boolean savedInstanceStatedrawer;

    public NavigationDrawer() {
        // Required empty public constructor
    }
    //*******************************************************************************
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        drawerExisted = Boolean.valueOf(readPreferences(getActivity(),KEY_DRAWER_LAYOUT,"false"));
        if(savedInstanceState!=null)
        {
            savedInstanceStatedrawer = true;
        }
    }
    //*******************************************************************************
    public List<MenuItems> getData()
    {
       List<MenuItems> menuList = new ArrayList<>();
       String [] arrMenuItems = {"Wish List","Our Team","Blog","Donations","Volunteer Program","Staff","Adoption Process"};

        for(int count = 0 ; count < arrMenuItems.length; count++)
        {
            MenuItems objMenuItems = new MenuItems();
            objMenuItems.menuItemName = arrMenuItems[count];
            menuList.add(objMenuItems);
        }

        return menuList;
    }
    //*******************************************************************************
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View viewlayout = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        recyclerView = (RecyclerView) viewlayout.findViewById(R.id.recycler_view);
        adapter = new MenuAdapter(getActivity(),getData());
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        return viewlayout;
    }
    //*******************************************************************************
    public void setUp(int fragId, DrawerLayout drawer, Toolbar toolbar)
    {
        container = getActivity().findViewById(fragId);
        drawerLayout = drawer;
        toggleDrawer = new ActionBarDrawerToggle(getActivity(),drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_closed)
        {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if(!drawerExisted)
                {
                    drawerExisted = true;
                    savePreferences(getActivity(),KEY_DRAWER_LAYOUT,drawerExisted +"");

                }
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }
        };

        if(!drawerExisted && !savedInstanceStatedrawer){
            drawerLayout.openDrawer(container);
        }
        drawerLayout.addDrawerListener(toggleDrawer);
        drawerLayout.post(new Runnable() {
            @Override
            public void run() {
                toggleDrawer.syncState();
            }
        });
    }
    //*******************************************************************************
    public void savePreferences(Context context,String prefName, String prefValue)
    {
        SharedPreferences preferences = context.getSharedPreferences(PREF_FILE_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(prefName,prefValue);
        editor.apply();
    }
    //*******************************************************************************
    public String readPreferences(Context context,String prefName, String defaultVal)
    {
        SharedPreferences preferences = context.getSharedPreferences(PREF_FILE_NAME,Context.MODE_PRIVATE);
        return preferences.getString(prefName,defaultVal);
    }
    //*******************************************************************************
    @Override
    public void recycleItemClick(View v, int position)
    {
        Intent newActivity = new Intent();
        if (position == 0)
        {
            newActivity = new Intent(getActivity(), WishListProcess1.class);
        }
        else if(position == 1)
        {
            newActivity = new Intent(getActivity(), OurTeam.class);
        }
        else if(position == 2)
        {
            newActivity = new Intent(getActivity(), BlogPosts.class);
        }
        else if(position == 3)
        {
            newActivity = new Intent(getActivity(),DonationScreen.class);
        }
        else if(position == 4)
        {
            newActivity = new Intent(getActivity(),VolunteerProcess1.class);
        }
        else if(position == 5)
        {
            Toast.makeText(getActivity(),"Under Constructions",Toast.LENGTH_SHORT).show();
        }
        else if(position == 6)
        {
            newActivity = new Intent(getActivity(),AdoptionProcess1.class);
        }
        startActivity(newActivity);
    }
}
