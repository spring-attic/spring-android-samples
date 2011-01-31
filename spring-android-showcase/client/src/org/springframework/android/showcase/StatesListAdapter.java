package org.springframework.android.showcase;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class StatesListAdapter extends BaseAdapter 
{
	private List<State> _states;
	private final LayoutInflater _layoutInflater;

	public StatesListAdapter(Context context, List<State> states) 
	{
		_states = states;
		_layoutInflater = LayoutInflater.from(context);
	}

	public int getCount() 
	{
		return _states.size();
	}

	public State getItem(int position) 
	{
		return (State) _states.get(position);
	}

	public long getItemId(int position) 
	{
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) 
	{
		State state = getItem(position);
		
		View view = convertView;
		
		if (view == null)
		{
			view = _layoutInflater.inflate(R.layout.states_list_item, parent, false);
		}

		TextView t = (TextView) view.findViewById(R.id.state_name);
		String formattedName = state.getName() + " (" + state.getAbbreviation() + ")"; 
		t.setText(formattedName);
		
		return view;
	}
}
