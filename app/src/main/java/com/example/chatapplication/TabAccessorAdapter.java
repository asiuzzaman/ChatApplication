package com.example.chatapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class TabAccessorAdapter extends FragmentPagerAdapter {

    public TabAccessorAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:{
                ChatsFragment chatsFragment=new ChatsFragment();
                return chatsFragment;
            }
            case 1:{
                GroupsFragment groupsFragment=new GroupsFragment();
                return groupsFragment;
            }
            case 2:{
                ContactsFragment contactsFragment=new ContactsFragment();
                return contactsFragment;
            }
            default: {
                return null;
            }
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        //return super.getPageTitle(position);
        switch (position){
            case 0:
                return "Chats";
            case 1:
                return "Groups";
            case 2:
                return "Contacts";
            default:
                return null;
        }

    }

    @Override
    public int getCount() {
        return 3;
    }
}
