package com.sunmi.msrtests.ui.fdrc;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.checkbox.MaterialCheckBox;
import com.sunmi.msrtests.R;
import com.sunmi.msrtests.data.entity.TestCaseStatus;
import com.sunmi.msrtests.model.TestcaseGroup;
import com.sunmi.msrtests.utilities.Enums;

public class TestcaseExpandableListAdapter extends BaseExpandableListAdapter {

    private Context c;
    private List<TestcaseGroup> testCaseGroupList;
    private LayoutInflater inflater;
    private FDRCViewModel viewModel;

    public TestcaseExpandableListAdapter(Context c, FDRCViewModel viewModel)
    {
        this.c=c;
        this.viewModel =viewModel;
        testCaseGroupList = viewModel.getTestCaseGroups();
        inflater=(LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    //GET A SINGLE PLAYER
    @Override
    public Object getChild(int groupPos, int childPos) {
        // TODO Auto-generated method stub
        return testCaseGroupList.get(groupPos).testCases.get(childPos);
    }

    //GET PLAYER ID
    @Override
    public long getChildId(int arg0, int arg1) {
        // TODO Auto-generated method stub
        return 0;
    }

    //GET PLAYER ROW
    @Override
    public View getChildView(int groupPos, int childPos, boolean isLastChild, View convertView,
                             ViewGroup parent) {

        //ONLY INFLATER XML ROW LAYOUT IF ITS NOT PRESENT,OTHERWISE REUSE IT

        if(convertView==null)
        {
            convertView=inflater.inflate(R.layout.list_testcase_item, null);
        }

        //GET CHILD/PLAYER NAME
        TestCaseStatus testCase=(TestCaseStatus) getChild(groupPos, childPos);
        TestcaseGroup testcaseGroup=(TestcaseGroup) getGroup(groupPos);     
        //SET CHILD NAME
        TextView nameTv=(TextView) convertView.findViewById(R.id.expandedListItem);
        nameTv.setText(testCase.testCaseNumber);
        MaterialCheckBox filterCheckBox=(MaterialCheckBox) convertView.findViewById(R.id.itemCheckbox);
        ImageView statusImage=(ImageView) convertView.findViewById(R.id.tcStatusImage);
        filterCheckBox.setVisibility(testCase.getDisplaySelection()?View.VISIBLE: View.GONE);
        statusImage.setVisibility(!testCase.getDisplaySelection()?View.VISIBLE: View.GONE);
        if(testCase.getDisplaySelection()) {
            filterCheckBox.setChecked(testCase.isSelected);
            filterCheckBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TestCaseStatus testCase = (TestCaseStatus) getChild(groupPos, childPos);
                    TestcaseGroup testcaseGroup = (TestcaseGroup) getGroup(groupPos);
                    if (filterCheckBox.isChecked()) {
                        testCase.isSelected = true;
                        Boolean allSelected = true;
                        for (TestCaseStatus status : testcaseGroup.testCases
                        ) {
                            if (status.isSelected == false) {
                                allSelected = false;
                                break;
                            }

                        }
                        if (allSelected) {
                            testcaseGroup.isSelected = true;
                            notifyDataSetChanged();
                        }
                    } else {
                        testCase.isSelected = false;
                        Boolean prevGroupSelectedVal = testcaseGroup.isSelected;
                        testcaseGroup.isSelected = false;
                        if (prevGroupSelectedVal) {
                            notifyDataSetChanged();
                        }


                    }
                }
            });
        }else{
            if(testCase.executionStatus == Enums.TestCaseExecutionStatus.Passed)
            {
                statusImage.setImageResource(R.drawable.ic_action_pass);
            }else if(testCase.executionStatus == Enums.TestCaseExecutionStatus.Failed){
                statusImage.setImageResource(R.drawable.ic_action_not_pass);
            }else{
                statusImage.setImageResource(R.drawable.ic_action_unknown);
            }
        }


        return convertView;
    }


    //GET NUMBER OF PLAYERS
    @Override
    public int getChildrenCount(int groupPosw) {
        // TODO Auto-generated method stub
        return testCaseGroupList.get(groupPosw).testCases.size();
    }

    //GET TEAM
    @Override
    public Object getGroup(int groupPos) {
        // TODO Auto-generated method stub
        return testCaseGroupList.get(groupPos);
    }

    //GET NUMBER OF TEAMS
    @Override
    public int getGroupCount() {
        // TODO Auto-generated method stub
        return testCaseGroupList.size();
    }

    //GET TEAM ID
    @Override
    public long getGroupId(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    //GET TEAM ROW
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        //ONLY INFLATE XML TEAM ROW MODEL IF ITS NOT PRESENT,OTHERWISE REUSE IT
        if(convertView == null)
        {
            convertView=inflater.inflate(R.layout.list_group_header, null);
        }

        //GET GROUP/TEAM ITEM
        TestcaseGroup testcaseGroup=(TestcaseGroup) getGroup(groupPosition);

        //SET GROUP NAME
        TextView nameTv=(TextView) convertView.findViewById(R.id.groupTitle);
        MaterialCheckBox filterCheckBox=(MaterialCheckBox) convertView.findViewById(R.id.groupCheckbox);
        
        filterCheckBox.setVisibility(testcaseGroup.getDisplaySelection()?View.VISIBLE: View.GONE);
        
        filterCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TestcaseGroup testcaseGroup=(TestcaseGroup) getGroup(groupPosition);      
                if( filterCheckBox.isChecked()){
                    testcaseGroup.isSelected = true;
                }else{
                     testcaseGroup.isSelected = false;
                }
                for (TestCaseStatus status:testcaseGroup.testCases
                     ) {
                      status.isSelected = filterCheckBox.isChecked();
                }

                notifyDataSetChanged();
            }
        });
        String name=testcaseGroup.name; 
        nameTv.setText(name);        
        filterCheckBox.setChecked(testcaseGroup.isSelected);      
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isChildSelectable(int arg0, int arg1) {
        // TODO Auto-generated method stub
        return true;
    }

}