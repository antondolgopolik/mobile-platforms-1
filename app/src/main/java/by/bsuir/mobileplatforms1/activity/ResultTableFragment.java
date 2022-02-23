package by.bsuir.mobileplatforms1.activity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.List;

import by.bsuir.mobileplatforms1.R;
import by.bsuir.mobileplatforms1.entity.Result;
import by.bsuir.mobileplatforms1.service.ResultService;

public class ResultTableFragment extends Fragment {

    public ResultTableFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_result_table, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Заполнить таблицу
        ResultService resultService = new ResultService(getContext());
        List<Result> results = resultService.getAllResults();
        TableLayout tableLayout = view.findViewById(R.id.tableLayout);
        for (Result result : results) {
            TableRow tableRow = (TableRow) getLayoutInflater().inflate(R.layout.tablerow_result_table, null);
            ((TextView) tableRow.findViewById(R.id.textView_cell1)).setText(result.getUser().getUsername());
            ((TextView) tableRow.findViewById(R.id.textView_cell2)).setText(String.valueOf(result.getPoints()));
            ((TextView) tableRow.findViewById(R.id.textView_cell3)).setText(result.getCreatedAt().toString());
            tableLayout.addView(tableRow);
        }
    }
}