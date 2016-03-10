package com.nowandroid.yanparser.FitFragments;


import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.app.ProgressDialog;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.nowandroid.yanparser.FitAdapters.PostBaseAdapter;
import com.nowandroid.yanparser.HelpParser.ParsingClass;
import com.nowandroid.yanparser.HelpParser.PostValue;
import com.nowandroid.yanparser.R;

import java.util.ArrayList;


public class FragmentList extends ListFragment implements AdapterView.OnItemClickListener {

    DetailsFragment detailsFragment;
    FragmentTransaction fragmentTransaction;
    ArrayList<PostValue> postValueArrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        detailsFragment = new DetailsFragment();
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getListView().setOnItemClickListener(this);
        new MyTask().execute();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        fragmentTransaction = getFragmentManager().beginTransaction();
        String linkv = String.valueOf(Uri.parse(postValueArrayList.get(position).getLink()));
        Bundle bundle = new Bundle();
        bundle.putString("link", linkv);
        detailsFragment.setArguments(bundle);

        fragmentTransaction.replace(R.id.frameContainer, detailsFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    class MyTask extends AsyncTask<Void, Void, Void> {
        ProgressDialog dialog;
        ParsingClass pc;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(getActivity());
            dialog.setMessage("Загрузка новостей...");
            dialog.setIndeterminate(true);
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            pc = new ParsingClass();
            pc.get();
            postValueArrayList = pc.getPostsList();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            PostBaseAdapter adapter = new PostBaseAdapter(getActivity(), postValueArrayList);
            setListAdapter(adapter);
            dialog.dismiss();
        }
    }

}
