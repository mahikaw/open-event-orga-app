package org.fossasia.openevent.app.event;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.fossasia.openevent.app.R;
import org.fossasia.openevent.app.common.BaseFragment;
import org.fossasia.openevent.app.event.attendees.AttendeesFragment;
import org.fossasia.openevent.app.event.detail.EventDetailFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EventContainerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventContainerFragment extends BaseFragment {

    @BindView(R.id.navigation)
    BottomNavigationView navigation;

    private long initialEventId;

    public static final String FRAG_DETAILS = "details";
    public static final String FRAG_ATTENDEES = "attendees";

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = item -> {
        switch (item.getItemId()) {
            case R.id.navigation_details:
                loadFragment(FRAG_DETAILS);
                return true;
            case R.id.navigation_attendees:
                loadFragment(FRAG_ATTENDEES);
                return true;
            default:
                loadFragment(FRAG_DETAILS);
                return true;
        }
    };

    public EventContainerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param initialEventId an event id for which the fragment is created.
     * @return A new instance of fragment EventContainerFragment.
     */
    public static EventContainerFragment newInstance(long initialEventId) {
        EventContainerFragment fragment = new EventContainerFragment();
        fragment.setInitialEvent(initialEventId);
        return fragment;
    }

    public void setInitialEvent(long initialEventId) {
        this.initialEventId = initialEventId;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_event_container, container, false);
        ButterKnife.bind(this, view);

        setRetainInstance(true);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        if(savedInstanceState == null) {
            loadFragment(FRAG_DETAILS);
        }
    }

    private void loadFragment(String tag) {
        FragmentManager fm = getChildFragmentManager();
        Fragment fg = fm.findFragmentByTag(tag);
        if(fg == null) {
            switch (tag) {
                case FRAG_DETAILS:
                    fg = EventDetailFragment.newInstance(initialEventId);
                    break;
                case FRAG_ATTENDEES:
                    fg = AttendeesFragment.newInstance(initialEventId);
                    break;
                default:
                    fg = EventDetailFragment.newInstance(initialEventId);
                    break;
            }
        }
        fm.beginTransaction()
            .replace(R.id.event_container, fg, tag)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            .addToBackStack(null)
            .commit();
    }

}
