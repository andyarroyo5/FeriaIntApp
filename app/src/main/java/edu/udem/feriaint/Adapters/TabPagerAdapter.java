package edu.udem.feriaint.Adapters;

import android.content.Context;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;


import edu.udem.feriaint.Activities.MainActivity;
import edu.udem.feriaint.Fragment.Fragment_ContenidoCultural;
import edu.udem.feriaint.Fragment.Fragment_Evento;
import edu.udem.feriaint.Fragment.Fragment_Perfil;
import edu.udem.feriaint.Modelos.Edicion;
import edu.udem.feriaint.Parser.EdicionJSON;
import edu.udem.feriaint.R;
import edu.udem.feriaint.Fragment.Fragment_Home;

/**
 * Created by Andrea Arroyo on 06/10/2016.
 */

public class TabPagerAdapter extends FragmentStatePagerAdapter {

    private String TAG;
    private Edicion edicion;
    public TabLayout tabLayout;
    public Context context;

    //private TabLayout tabLayout;
    private int[] tabIcons = {
            R.drawable.ic_home_white_24dp,
            R.drawable.ic_event_white_24dp,
            R.drawable.ic_language_white_24dp,
            R.drawable.ic_perm_identity_white_24dp,
    };

    public TabPagerAdapter(FragmentManager fm) {
        super(fm);
    }



    public void setupTabIcons(TabLayout tabLayout, Context context) {
        this.tabLayout=tabLayout;
        this.context=context;
        tabLayout.getTabAt(0).setIcon( R.drawable.ic_home_white_24dp);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
        tabLayout.getTabAt(3).setIcon(tabIcons[3]);


    }

    public void setColorTabs(int posicion)
    {

        this.tabLayout.getTabAt(0).getIcon().setColorFilter(context.getResources().getColor(R.color.primary_text), PorterDuff.Mode.SRC_IN);
        this.tabLayout.getTabAt(1).getIcon().setColorFilter(context.getResources().getColor(R.color.primary_text), PorterDuff.Mode.SRC_IN);
        this.tabLayout.getTabAt(2).getIcon().setColorFilter(context.getResources().getColor(R.color.primary_text), PorterDuff.Mode.SRC_IN);
        this.tabLayout.getTabAt(3).getIcon().setColorFilter(context.getResources().getColor(R.color.primary_text), PorterDuff.Mode.SRC_IN);

        this.tabLayout.getTabAt(posicion).getIcon().setColorFilter(ContextCompat.getColor(context, R.color.accent),PorterDuff.Mode.SRC_ATOP);


    }



    @Override
    public int getCount() {
        // Show 3 total pages.
        return 4;
    }


    @Override
    public Fragment getItem(int position) {
        switch (position) {

            case 0: // siempre empieza desde 0

                Fragment_Home home=new Fragment_Home();

                return home;

            case 1:
                return new Fragment_Evento();

            case 2:

                return new Fragment_ContenidoCultural();


            case 3:
                return new Fragment_Perfil();

            // si la posición recibida no se corresponde a ninguna sección
            default:
                return null;
        }
    }


    @Override
    public CharSequence getPageTitle(int position) {
        /*switch (position) {
            case 0:
                return "Home";
            case 1:
                return "Eventos";
            case 2:
                return "Cultura";
            case 3:
                return "Perfil";
        }*/
        return null;
    }



}
