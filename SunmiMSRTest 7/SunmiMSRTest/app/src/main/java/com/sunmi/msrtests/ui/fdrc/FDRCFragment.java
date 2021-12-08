package com.sunmi.msrtests.ui.fdrc;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.sunmi.msrtests.R;
import com.sunmi.msrtests.configuration.GlobalState;
import com.sunmi.msrtests.data.entity.FDRCTestCase;
import com.sunmi.msrtests.data.entity.TestCaseStatus;
import com.sunmi.msrtests.data.repository.TestCaseRepository;
import com.sunmi.msrtests.workers.SaleTestcaseHandler;

public class FDRCFragment extends Fragment {

    private FDRCViewModel FDRCViewModel;
    private ExpandableListView elv;
    private ProgressBar progressBar;
    private Button resetTestButton;
    private Button executeTestButton;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GlobalState.getInstance().setAppContext(this.getActivity().getApplication());
        FDRCViewModel =
                new ViewModelProvider(this).get(FDRCViewModel.class);
        View root = inflater.inflate(R.layout.fragment_fdrc, container, false);
        elv = (ExpandableListView) root.findViewById(R.id.expandableListView);
        progressBar = (ProgressBar) root.findViewById(R.id.progressBar);
        executeTestButton = (Button) root.findViewById(R.id.exeuteButton);
        resetTestButton = (Button) root.findViewById(R.id.resetButton);

        // Create the observer which updates the UI.
        final Observer<Boolean> isBusyObserver = new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable final Boolean newValue) {
                // Update the UI, in this case, a TextView.
                if(newValue ==null)
                    return;
                if(newValue.booleanValue())
                {
                    progressBar.setVisibility(View.VISIBLE);
                }else
                {
                    progressBar.setVisibility(View.GONE);
                }
            }
        };

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        FDRCViewModel.getIsBusy().observe(getViewLifecycleOwner(), isBusyObserver);

        // Create the observer which updates the UI.
        final Observer<Boolean> isDataSyncedObserver = new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable final Boolean newValue) {
                // Update the UI, in this case, a TextView.
                if(newValue ==null)
                    return;
                if(newValue)
                    initializeListView();
                else
                    FDRCViewModel.fetchTestCasesFromAssets();

            }
        };

        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        FDRCViewModel.getIsDataSynced().observe(getViewLifecycleOwner(), isDataSyncedObserver);

        // Create the observer which updates the UI.
        final Observer<Boolean> isTestInProgressObserver = new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable final Boolean newValue) {
                // Update the UI, in this case, a TextView.
                if(newValue ==null)
                    return;
                executeTestButton.setEnabled(!newValue);
                resetTestButton.setEnabled(!newValue);
            }
        };
        // Observe the LiveData, passing in this activity as the LifecycleOwner and the observer.
        FDRCViewModel.getIsTestInProgress().observe(getViewLifecycleOwner(), isTestInProgressObserver);


        executeTestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                // Do something
                FDRCViewModel.startExecuteSelectedTestcase();
            }
        });
        return root;
    }

    private void initializeListView(){
        //CREATE AND BIND TO ADAPTER
        TestcaseExpandableListAdapter adapter = new TestcaseExpandableListAdapter(getContext(), FDRCViewModel);
        elv.setAdapter(adapter);
        TestCaseRepository repo = new TestCaseRepository(GlobalState.getInstance().getContext());
        FDRCTestCase abcd = repo.getFirstTestCase();
        TestCaseStatus hello = repo.getTestcaseStatusForTestcaseNumber(abcd.testCaseNumber);
        SaleTestcaseHandler a = new SaleTestcaseHandler(hello);
        a.executeTestcase();
    }
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}