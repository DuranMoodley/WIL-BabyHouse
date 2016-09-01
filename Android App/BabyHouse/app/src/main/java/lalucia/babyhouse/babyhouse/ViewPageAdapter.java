package lalucia.babyhouse.babyhouse;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class ViewPageAdapter extends FragmentStatePagerAdapter{

    CharSequence pageTitles [];
    int numberOfPages;
    //***************************************************************************************
    public ViewPageAdapter(FragmentManager fm, CharSequence titles[], int pages) {
        super(fm);
        pageTitles = titles;
        numberOfPages = pages;
    }
    //***************************************************************************************
    @Override
    public Fragment getItem(int position)
    {
        FragHome home = new FragHome();
        if(position == 0)
        {
            return home;
        }
        else if(position == 1)
        {
            FragCheckIn checkIn = new FragCheckIn();
            return checkIn;
        }
        else if(position == 2)
        {
            FragDonate donate = new FragDonate();
            return donate;
        }
        else if(position == 3)
        {
            FragEvents events = new FragEvents();
            return events;
        }
        return home;
    }
    //***************************************************************************************
    @Override
    public CharSequence getPageTitle(int position) {
        return pageTitles[position];
    }
    //***************************************************************************************
    @Override
    public int getCount() {
        return numberOfPages;
    }
}
