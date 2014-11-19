package ar.uba.fi.mileem;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Intents;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import ar.uba.fi.mileem.models.PublicationFullResult;

public class ContactFragment extends Fragment implements IPublicationDataObserver {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
		View rootView = inflater.inflate(R.layout.fragment_contact, container, false);
		rootView.findViewById(R.id.contact_layout).setVisibility(View.GONE);
		setViewInfo(rootView);
		return rootView;
	}

	
	private void setViewInfo(View v){
		PublicationActivity a = ((PublicationActivity) getActivity());
		PublicationFullResult p =  a.getPublication();
		if(p!= null && v != null){
			ImageButton addContact =  (ImageButton) v.findViewById(R.id.add_contact);
			addContact.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					PublicationActivity a = ((PublicationActivity) getActivity());
					PublicationFullResult p =  a.getPublication();
					Intent intent = new Intent(Intents.Insert.ACTION);
					intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
					intent.putExtra(Intents.Insert.NAME, p.getContactName() + " (MiLEEM)");
					intent.putExtra(Intents.Insert.EMAIL, p.getContactEmail());
					intent.putExtra(Intents.Insert.SECONDARY_PHONE, p.getHomePhone());
					intent.putExtra(Intents.Insert.PHONE, p.getMobilePhone());
					startActivity(intent);
				}
			});
			TextView contact_name = (TextView) v.findViewById(R.id.contact_name);
			contact_name.setText(p.getContactName());
			TextView home_phone = (TextView) v.findViewById(R.id.contact_home_phone);
			home_phone.setText(p.getHomePhone());
			v.findViewById(R.id.contact_home_phone_layout).setVisibility((p.getHomePhone().equals(""))?View.GONE:View.VISIBLE);

			TextView mobile_phone = (TextView) v.findViewById(R.id.contact_mobile_phone);
			mobile_phone.setText(p.getMobilePhone());
			v.findViewById(R.id.contact_mobile_phone_layout).setVisibility((p.getMobilePhone().equals(""))?View.GONE:View.VISIBLE);
			
			TextView email = (TextView) v.findViewById(R.id.contact_email);
			email.setText(p.getContactEmail());
			
			v.findViewById(R.id.contact_layout).setVisibility(View.VISIBLE);
		}
		
	}
	
	
	
	public void onPublicationData() {
		setViewInfo(getView());		
	}
}
