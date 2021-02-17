package com.headmostlab.notes.ui.note;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.headmostlab.notes.databinding.FragmentNoteBinding;
import com.headmostlab.notes.model.Note;

import java.text.DateFormat;
import java.util.Date;

public class NoteFragment extends Fragment implements NoteContract.View {

    public static final String NOTE_KEY = "NOTE";
    private FragmentNoteBinding binding;
    private NotePresenter presenter;

    public static NoteFragment newNoteFragment(Note note) {
        NoteFragment fragment = new NoteFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(NOTE_KEY, note);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        boolean isPortrait = getResources().getConfiguration().orientation ==
                Configuration.ORIENTATION_PORTRAIT;

        if (!isPortrait) {
            getFragmentManager().popBackStack();
        }

        presenter = new ViewModelProvider(this,
                new NoteViewModelFactory(this, null)).get(NotePresenter.class);
        if (getArguments() != null) {
            presenter.setNote(getArguments().getParcelable(NOTE_KEY));
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentNoteBinding.inflate(inflater, container, false);
        MaterialDatePicker<Long> picker = MaterialDatePicker.Builder.datePicker().build();
        picker.addOnPositiveButtonClickListener(selection ->
                presenter.setCreateDate(new Date(selection)));
        binding.pickDateButton.setOnClickListener(v ->
                picker.show(getFragmentManager(), picker.toString()));
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        presenter.takeView(this);
    }

    @Override
    public void show(Note note) {
        binding.title.setText(note.getTitle());
        binding.description.setText(note.getDescription());
        binding.createDate.setText(DateFormat.getDateInstance().format(note.getCreationDate()));
    }
}
