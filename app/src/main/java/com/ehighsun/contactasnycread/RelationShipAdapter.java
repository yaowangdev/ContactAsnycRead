package com.ehighsun.contactasnycread;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;


import java.util.HashMap;
import java.util.List;

/**
 * Created by zlp on 2017/6/6.
 */

public class RelationShipAdapter extends BaseAdapter implements SectionIndexer{

    private List<Contacts> contacts;
    private Context context;
    private LayoutInflater inflater;
    private HashMap<String, Integer> alphaIndexer;
    private String[] sections={ "A", "B", "C", "D", "E", "F", "G", "H",
            "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
            "V", "W", "X", "Y", "Z" ,"#"};

    public RelationShipAdapter(List<Contacts> contacts, Context context) {
        this.contacts = contacts;
        this.context = context;
        this.alphaIndexer = new HashMap<>();
        this.inflater = LayoutInflater.from(context);
        for (int i =0; i <contacts.size(); i++) {
            String name = contacts.get(i).getSortKey();
            if(!alphaIndexer.containsKey(name)){//只记录在list中首次出现的位置
                alphaIndexer.put(name, i);
            }
        }
    }

    @Override
    public int getCount() {
        return contacts.size();
    }

    @Override
    public Object getItem(int position) {
        return contacts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView == null){
            holder=new ViewHolder();
            convertView = inflater.inflate(R.layout.content_item,parent,false);
            holder.sortkeyLayout = convertView.findViewById(R.id.content_title);
            holder.tvName = convertView.findViewById(R.id.content_name);
            holder.tvKey = holder.sortkeyLayout.findViewById(R.id.content_key);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tvName.setText(contacts.get(position).getPhontName());

        int section = getSectionForPosition(position);
        int pos = getPositionForSection(section);
        Log.d("onAdapter","section=>"+section);
        Log.d("onAdapter","position=>"+position);
        Log.d("onAdapter","pos=>"+pos);
        if(position== pos){
            holder.tvKey.setText(contacts.get(position).getSortKey());
            holder.sortkeyLayout.setVisibility(View.VISIBLE);
        }else {
            holder.sortkeyLayout.setVisibility(View.GONE);
        }
        return convertView;
    }

    @Override
    public Object[] getSections() {
        return sections;
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        try {
            if(sectionIndex<0){
                sectionIndex=0;
            }
            if(sectionIndex>sections.length-1){
                sectionIndex=sections.length-1;
            }
            String later = sections[sectionIndex];
            if(alphaIndexer.containsKey(later)){
                return alphaIndexer.get(later);
            }
        }catch (Exception e) {

        }
        return -1;
    }

    @Override
    public int getSectionForPosition(int position) {
        try {
            if(position>contacts.size()-1){
                position = contacts.size()-1;
            }
            if(position<0){
                position = 0;
            }
            String key = contacts.get(position).getSortKey();
            for (int i = 0; i < sections.length; i++) {
                if (sections[i].equals(key)) {
                    return i;
                }
            }
        }catch (Exception e){

        }
        return -1;
    }


    class ViewHolder{
        private TextView tvKey;
        private TextView tvName;
        private LinearLayout sortkeyLayout;
    }
}
