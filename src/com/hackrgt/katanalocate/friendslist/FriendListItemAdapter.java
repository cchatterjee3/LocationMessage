package com.hackrgt.katanalocate.friendslist;

import java.util.ArrayList;

import com.hackrgt.katanalocate.R;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FriendListItemAdapter extends ArrayAdapter<Friend> {

	private ArrayList<Friend> friends;
	private ArrayList<String> appUsersId;
	private static final String TAG = "FriendListItemAdapter";
	
	public FriendListItemAdapter(Context context, int textViewResourceId, ArrayList<Friend> friends) {
		super(context, textViewResourceId, friends);
		this.friends = friends;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater vi = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.friend_list_single_item, null);
		}
		
		//Get defect form in list and display its details
		Friend friend = friends.get(position);
		if (friend != null) {
			
			//Display friend's name
			TextView name = (TextView) v.findViewById(R.id.name);
			if (name != null)
				name.setText(friend.getName());
			
			//Display friend's profile picture
			ImageView profilePic = (ImageView) v.findViewById(R.id.userImage);
			if (profilePic != null) {
				try {
					profilePic.setImageBitmap(friend.getImgBitmap());
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			
			if (friendIsAppUser(friend)) {
				//System.out.println(friend.getName()+" has the app");
				friend.setAppUser(true);
				
				//Remove text inviting player to download application
				TextView inviteFriend = (TextView) v.findViewById(R.id.sendInvite);
				inviteFriend.setVisibility(View.INVISIBLE);
				
				//Show has application icon
				ImageView hasAppIcon = (ImageView) v.findViewById(R.id.hasAppIcon);
				hasAppIcon.setVisibility(View.VISIBLE);
			}
			else {
				friend.setAppUser(true);
				TextView inviteFriend = (TextView) v.findViewById(R.id.sendInvite);
				inviteFriend.setVisibility(View.VISIBLE);
				ImageView hasAppIcon = (ImageView) v.findViewById(R.id.hasAppIcon);
				hasAppIcon.setVisibility(View.INVISIBLE);
			}
		}
			
		return v;
	}
	
	
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        //albumButton.setText(songs.get(firstVisibleItem).getAlbum());
    	System.out.println("Scrolling");
    }

	private boolean friendIsAppUser(Friend friend) {
		Log.d(TAG,friend.getId());
		for (String id : appUsersId) {
			if (id.equals(friend.getId())) {
				return true;
			}
		}
		//Return false if no matches found
		return false;
	}
	
	public void setUsersId(ArrayList<String> appUsersId) {
		this.appUsersId = appUsersId;
	}
}
