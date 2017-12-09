package br.com.eduardo.dudazap.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import br.com.eduardo.dudazap.fragment.ContatosFragment;
import br.com.eduardo.dudazap.fragment.ConversasFragment;

/**
 * Created by Eduardo on 08/12/2017.
 */

public class TabAdapter extends FragmentStatePagerAdapter {

    private String[] tituloAbas = {"CONVERSAS","CONTATO"};

    public TabAdapter(FragmentManager fn){
        super(fn);
    }


    @Override
    public Fragment getItem(int position) {
        Fragment fragmento = null;

        switch (position){
            case 0:
                fragmento = new ConversasFragment();
                break;
            case 1:
                fragmento = new ContatosFragment();
                break;
        }

        return fragmento;
    }

    @Override
    public int getCount() {
        return tituloAbas.length;
    }


    @Override
    public  CharSequence getPageTitle(int position){
        return tituloAbas[position];
    }

}
