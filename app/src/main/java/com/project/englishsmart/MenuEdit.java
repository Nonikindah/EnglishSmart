package com.project.englishsmart;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedHashMap;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MenuEdit.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MenuEdit#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MenuEdit extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button btn_edit;
    DatabaseHelper MyDb;
    private OnFragmentInteractionListener mListener;
    private ArrayList<String> Exercise;
    private ArrayList<Integer> id;


    public MenuEdit() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MenuEdit.
     */
    // TODO: Rename and change types and number of parameters
    public static MenuEdit newInstance(String param1, String param2) {
        MenuEdit fragment = new MenuEdit();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_menu_edit);
        MyDb = new DatabaseHelper(this.getActivity());

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    class CustomAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return Exercise.size();
        }

        @Override
        public Object getItem(int i) {
            return Exercise.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.listlayout,null);
            final TextView textView_exercise = (TextView)view.findViewById(R.id.textView2);

            textView_exercise.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    Intent i = new Intent(MenuEdit.this.getActivity(), FormEdit.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    i.putExtra("STUDY", ((TextView) view.findViewById(R.id.textView2)).getText());
                    i.putExtra("ID", ((TextView) view.findViewById(R.id.textView2)).getId());
                    MenuEdit.this.startActivity(i);
                }
            });
            textView_exercise.setText(Exercise.get(i));
            return view;
        }
    }

    private void setContentView(int fragment_menu_edit) {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fetch();
        //Toast.makeText(getActivity(),"Edit",Toast.LENGTH_SHORT).show();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_menu_edit, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btn_edit = (Button) this.getActivity().findViewById(R.id.edit_btn);
        btn_edit.setOnClickListener(this);

        ListView listView = (ListView) getActivity().findViewById(R.id.list_view);

        CustomAdapter customAdapter = new CustomAdapter();
        listView.setAdapter(customAdapter);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void fetch(){
        MyDb =new DatabaseHelper(this.getContext());
        try {

            MyDb.createDatabase();
            MyDb.openDatabase();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


        //namelist=new LinkedHashMap<>();
        int ii;
        int ic;
        SQLiteDatabase sd = MyDb.getReadableDatabase();
        Cursor cursor = sd.rawQuery("SELECT * FROM sentence",null);
        ii=cursor.getColumnIndex("sentence");
        ic=cursor.getColumnIndex("id");
        Exercise =new ArrayList<String>();
        id = new ArrayList<Integer>();
        //Log.d(TAG,"first");
        if (cursor.moveToFirst()) {
            do {
                Exercise.add(cursor.getString(ii));
                id.add(cursor.getInt(ic));
                //Toast.makeText(getContext(), "jumlah "+cursor.getInt(ic), Toast.LENGTH_SHORT).show();
//
            } while (cursor.moveToNext());
        }
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this.getActivity(), FormEdit.class);
        this.getActivity().startActivity(intent);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}