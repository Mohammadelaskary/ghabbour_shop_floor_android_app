package com.example.gbsbadrsf.Stoppages;

import static android.content.ContentValues.TAG;

import static com.example.gbsbadrsf.MainActivity.DEVICE_SERIAL_NO;
import static com.example.gbsbadrsf.MainActivity.context;
import static com.example.gbsbadrsf.MainActivity.userInfo;
import static com.example.gbsbadrsf.MyMethods.MyMethods.handleEditTextFocus;
import static com.example.gbsbadrsf.MyMethods.MyMethods.loadingProgressDialog;
import static com.example.gbsbadrsf.MyMethods.MyMethods.showSuccessAlerter;
import static com.example.gbsbadrsf.MyMethods.MyMethods.warningDialog;
import static com.example.gbsbadrsf.signin.SigninFragment.USER_ID;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.widget.TimePicker;

import com.example.gbsbadrsf.MainActivity;
import com.example.gbsbadrsf.MyMethods.MyMethods;
import com.example.gbsbadrsf.MyMethods.SetUpBarCodeReaderInterMec;
import com.example.gbsbadrsf.R;
import com.example.gbsbadrsf.SetUpBarCodeReader;
import com.example.gbsbadrsf.Stoppages.Model.AddStoppageData;
import com.example.gbsbadrsf.Stoppages.Model.Die;
import com.example.gbsbadrsf.Stoppages.Model.Factory;
import com.example.gbsbadrsf.Stoppages.Model.Jig;
import com.example.gbsbadrsf.Stoppages.Model.Machine;
import com.example.gbsbadrsf.Stoppages.Model.MachineDetail;
import com.example.gbsbadrsf.Stoppages.Model.MachineFamily;
import com.example.gbsbadrsf.Stoppages.Model.Station;
import com.example.gbsbadrsf.Stoppages.Model.StationDetails;
import com.example.gbsbadrsf.Stoppages.Model.StoppageName;
import com.example.gbsbadrsf.Stoppages.Model.WorkCenter;
import com.example.gbsbadrsf.Stoppages.Model.WorkCenterLine;
import com.example.gbsbadrsf.Stoppages.Model.WorkCenterSubLine;
import com.example.gbsbadrsf.Util.LocaleHelper;
import com.example.gbsbadrsf.databinding.FragmentAddStoppageBinding;
import com.honeywell.aidc.BarcodeFailureEvent;
import com.honeywell.aidc.BarcodeReadEvent;
import com.honeywell.aidc.BarcodeReader;
import com.honeywell.aidc.TriggerStateChangeEvent;
import com.intermec.aidc.BarcodeReadListener;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class AddStoppageFragment extends Fragment implements View.OnClickListener, BarcodeReadListener, BarcodeReader.BarcodeListener, BarcodeReader.TriggerListener {

    private AddStoppageViewModel viewModel;

    public static AddStoppageFragment newInstance() {
        return new AddStoppageFragment();
    }

    private FragmentAddStoppageBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentAddStoppageBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    private SetUpBarCodeReader barCodeReader;
    private SetUpBarCodeReaderInterMec barCodeReaderInterMec;
    private ProgressDialog progressDialog;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(this).get(AddStoppageViewModel.class);
        barCodeReader = new SetUpBarCodeReader(this,this);
        barCodeReaderInterMec = new SetUpBarCodeReaderInterMec(this);
        progressDialog = loadingProgressDialog(getContext());
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        handleMachineOrStationRadioGroup();
        initialDatePicker();

        viewModel.GetAvailableDatesForStoppage();
        observeGettingAvailableDates();
        observeGettingAvailableDatesStatus();

        viewModel.GetStoppagesNamesList();
        setUpStoppageNamesSpinner();
        observeGettingStoppageNames();
        observeGettingStoppageNamesStatus();

        viewModel.GetFactoriesList();
        setUpFactoriesListSpinner();
        observeGettingFactoriesList();
        observeGettingFactoriesListStatus();

        setUpWorkCentersSpinner();
        observeGettingWorkCentersList();
        observeGettingWorkCentersListStatus();

        setUpWorkCenterLinesSpinner();
        observeGettingWorkCenterLinesList();
        observeGettingWorkCenterLinesListStatus();

        setUpWorkCenterSubLinesSpinner();
        observeGettingWorkCenterSubLinesList();
        observeGettingWorkCenterSubLinesListStatus();

        setMachineFamilySpinner();
        observeGettingMachineFamilyList();
        observeGettingMachineFamilyListStatus();

        setUpMachinesSpinner();
        observeGettingMachinesList();
        observeGettingMachinesListStatus();

        setUpStationsSpinner();
        observeGettingStationsList();
        observeGettingStationsListStatus();

        attachButtonsToListeners();

        observeGettingMachineDetails();
        observeGettingMachineDetailsStatus();

        observeGettingStationDetails();
        observeGettingStationDetailsStatus();

        observeAddStoppage();
        observeAddStoppageStatus();

        viewModel.GetDiesList();
        setUpDiesListSpinner();
        observeGettingDiesList();
        observeGettingDiesListStatus();

        viewModel.GetJigsList();
        setUpJigsListSpinner();
        observeGettingJigsList();
        observeGettingJigsListStatus();

        handleEditTextFocus(binding.machine,binding.station);
    }

    private void observeGettingJigsListStatus() {
        viewModel.getJigsListStatus().observe(getViewLifecycleOwner(),status -> {
            switch (status){
                case LOADING:
                    binding.jigLoading.show();
                    break;
                case SUCCESS:
                    binding.jigLoading.hide();
                    break;
                case ERROR:
                    binding.jigLoading.hide();
                    warningDialog(getContext(),getString(R.string.network_issue));
                    break;
            }
        });
    }

    private void observeGettingJigsList() {
        viewModel.getJigsList().observe(getViewLifecycleOwner(),response ->{
            if (response!=null){
                String statusMessage = response.getResponseStatus().getStatusMessage();
                if (response.getResponseStatus().getIsSuccess()){
                    jigsList = response.getJig();
                    jigsAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item,jigsList);
                    binding.jigSpinner.setAdapter(jigsAdapter);
                } else warningDialog(getContext(),statusMessage);
            }
        });
    }

    private ArrayAdapter<Jig> jigsAdapter;
    private List<Jig> jigsList = new ArrayList<>();
    private int selectedJigId = -1;
    private void setUpJigsListSpinner() {
        binding.jigSpinner.setOnItemClickListener((parent, view, position, id) -> {
            selectedJigId = jigsList.get(position).getJigId();
        });
    }
    private void clearJig(){
        jigsList.clear();
        binding.jigSpinner.setText("");
        selectedJigId=-1;
    }


    private void observeGettingDiesListStatus() {
        viewModel.getDiesListStatus().observe(getViewLifecycleOwner(),status -> {
            switch (status){
                case LOADING:
                    binding.dieLoading.show();
                    break;
                case SUCCESS:
                    binding.dieLoading.hide();
                    break;
                case ERROR:
                    binding.dieLoading.hide();
                    warningDialog(getContext(),getString(R.string.network_issue));
                    break;
            }
        });
    }

    private void observeGettingDiesList() {
        viewModel.getDiesList().observe(getViewLifecycleOwner(),response ->{
            if (response!=null){
                String statusMessage = response.getResponseStatus().getStatusMessage();
                if (response.getResponseStatus().getIsSuccess()){
                    diesList = response.getDie();
                    diesAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item,diesList);
                    binding.dieSpinner.setAdapter(diesAdapter);
                } else warningDialog(getContext(),statusMessage);
            }
        });
    }

    private ArrayAdapter<Die> diesAdapter;
    private List<Die> diesList = new ArrayList<>();
    private int selectedDieId = -1;
    private void setUpDiesListSpinner() {
        binding.dieSpinner.setOnItemClickListener((parent, view, position, id) -> {
            selectedDieId = diesList.get(position).getDieId();
        });
    }
    private void clearDie(){
        diesList.clear();
        binding.dieSpinner.setText("");
        selectedDieId=-1;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void observeAddStoppage() {
        viewModel.getAddStoppage().observe(getViewLifecycleOwner(),response ->{
            if (response!=null){
                String statusMessage = response.getResponseStatus().getStatusMessage();
                if (response.getResponseStatus().getIsSuccess()){
                    showSuccessAlerter(statusMessage,getActivity());
                    clearStoppageName();
                    clearWorkCenter();
                    clearWorkCenterLines();
                    clearWorkCenterSubLines();
                    clearMachineFamily();
                    clearMachine();
                    clearStation();
                    clearDie();
                    clearJig();
                    binding.stoppageStartTime.getEditText().setText("");
                    binding.stoppageStartDate.getEditText().setText("");
                    binding.factorySpinner.setText("",false);
                    stoppageDate="";
                    stoppageTime = "";
                } else warningDialog(getContext(),statusMessage);
            } else
                warningDialog(getContext(),getString(R.string.error_in_getting_data));
        });
    }

    private void observeAddStoppageStatus() {
        viewModel.getAddStoppageStatus().observe(getViewLifecycleOwner(),status -> {
            switch (status){
                case LOADING:
                    progressDialog.show();
                    break;
                case SUCCESS:
                    progressDialog.hide();
                    break;
                case ERROR:
                    progressDialog.hide();
                    warningDialog(getContext(),getString(R.string.network_issue));
                    break;
            }
        });
    }

    private void observeGettingStationDetailsStatus() {
        viewModel.getStationDetailsStatus().observe(getViewLifecycleOwner(),status -> {
            switch (status) {
                case LOADING:
                    progressDialog.show();
                    break;
                case SUCCESS:
                    progressDialog.hide();
                    break;
                case ERROR:
                    warningDialog(getContext(), getString(R.string.network_issue));
                    progressDialog.hide();
                    break;
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void observeGettingStationDetails() {
        viewModel.getStationDetails().observe(getViewLifecycleOwner(),response ->{
            if (response!=null){
                String statusMessage = response.getResponseStatus().getStatusMessage();
                if (response.getResponseStatus().getIsSuccess()){
                    fillData(response.getMachineDetails());
                } else warningDialog(getContext(),statusMessage);
            } else warningDialog(getContext(),getString(R.string.error_in_getting_data));
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void fillData(StationDetails machineDetails) {
        binding.factorySpinner.setText(machineDetails.getFactoryName(),false);
        selectedFactoryId = machineDetails.getFactoryId();
        binding.workCenterSpinner.setText(machineDetails.getWorkCenterName(),false);
        selectedWorkCenterId = machineDetails.getWorkCenterId();
        binding.lineSpinner.setText(machineDetails.getProductionLineName(),false);
        selectedWorkCenterLineId = machineDetails.getProductionLineId();
        binding.sublineSpinner.setText(machineDetails.getProductionSubLineName(),false);
        selectedWorkCenterSubLineId = machineDetails.getProductionSubLineId();
        binding.stationSpinner.setText(machineDetails.getProductionStationName(),false);
        selectedStationId = machineDetails.getProductionStationId();
    }

    private void observeGettingMachineDetailsStatus() {
        viewModel.getMachineDetailsStatus().observe(getViewLifecycleOwner(),status -> {
            switch (status) {
                case LOADING:
                    progressDialog.show();
                    break;
                case SUCCESS:
                    progressDialog.hide();
                    break;
                case ERROR:
                    warningDialog(getContext(), getString(R.string.network_issue));
                    progressDialog.hide();
                    break;
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void observeGettingMachineDetails() {
        viewModel.getMachineDetails().observe(getViewLifecycleOwner(),response ->{
            if (response!=null){
                String statusMessage = response.getResponseStatus().getStatusMessage();
                if (response.getResponseStatus().getIsSuccess()){
                    fillData(response.getMachineDetails());
                } else warningDialog(getContext(),statusMessage);
            } else warningDialog(getContext(),getString(R.string.error_in_getting_data));
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void fillData(MachineDetail machineDetails) {
        binding.factorySpinner.setText(machineDetails.getFactoryName(),false);
        selectedFactoryId = machineDetails.getFactoryId();
        binding.workCenterSpinner.setText(machineDetails.getWorkCenterName(),false);
        selectedWorkCenterId = machineDetails.getWorkCenterId();
        binding.lineSpinner.setText(machineDetails.getProductionLineName(),false);
        selectedWorkCenterLineId = machineDetails.getProductionLineId();
        binding.sublineSpinner.setText(machineDetails.getProductionSubLineName(),false);
        selectedWorkCenterSubLineId = machineDetails.getProductionSubLineId();
        binding.machineFamilySpinner.setText(machineDetails.getMachineFamilyName(),false);
        selectedMachineFamilyId = machineDetails.getMachineFamilyId();
        binding.machineSpinner.setText(machineDetails.getMachineName(),false);
        selectedMachineId = machineDetails.getMachineId();
    }

    private void observeGettingAvailableDatesStatus() {
        viewModel.getAvailableDatesStatus().observe(getViewLifecycleOwner(),status -> {
            switch (status) {
                case LOADING:
                    binding.stoppageStartTimeLoading.show();
                    break;
                case SUCCESS:
                    binding.stoppageStartTimeLoading.hide();
                    break;
                case ERROR:
                    warningDialog(getContext(), getString(R.string.network_issue));
                    binding.stoppageStartTimeLoading.hide();
                    break;
            }
        });
    }

    private void observeGettingAvailableDates() {
        viewModel.getAvailableDates().observe(getViewLifecycleOwner(),response->{
            if (response!=null){
                String statusMessage = response.getResponseStatus().getStatusMessage();
                if (response.getResponseStatus().getIsSuccess()){
                    Calendar [] availableCalendars = formatDateTime(response.getAvailableDates());
                    dpd.setSelectableDays(availableCalendars);
                } else warningDialog(getContext(),statusMessage);
            }
        });
    }

    private Calendar[] formatDateTime(List<String> availableDates) {
        Calendar[] availableCalendars = new Calendar[availableDates.size()+1];
        for (int i = 0; i < availableDates.size(); i++) {
            Calendar calendar = Calendar.getInstance();
            int year = Integer.parseInt(availableDates.get(i).substring(0,4));
            int month = Integer.parseInt(availableDates.get(i).substring(5,7));
            int day  = Integer.parseInt(availableDates.get(i).substring(8,10));
            calendar.set(Calendar.YEAR,year);
            calendar.set(Calendar.MONTH,month-1);
            calendar.set(Calendar.DAY_OF_MONTH,day);
            availableCalendars[i]=calendar;
        }
        availableCalendars[availableDates.size()]=Calendar.getInstance();
        return availableCalendars;
    }

    //    private Calendar stringDateToCalendar(String date){
//        Calendar cal = Calendar.getInstance();
//
//    }
    private DatePickerDialog dpd;
    private String stoppageDate = "";
    private String stoppageTime = "";
    private void initialDatePicker() {
        Calendar now = Calendar.getInstance();
        dpd = DatePickerDialog.newInstance(
                (view, year, monthOfYear, dayOfMonth) -> {
                    int month = monthOfYear+1;
                    stoppageDate= year+"-"+month+"-"+dayOfMonth;
                    binding.stoppageStartDate.getEditText().setText(stoppageDate);
                },
                now.get(Calendar.YEAR), // Initial year selection
                now.get(Calendar.MONTH), // Initial month selection
                now.get(Calendar.DAY_OF_MONTH) // Inital day selection
        );
    }

    private void observeGettingFactoriesListStatus() {
        viewModel.getFactoriesListStatus().observe(getViewLifecycleOwner(),status -> {
            switch (status) {
                case LOADING:
                    binding.factoryLoading.show();
                    break;
                case SUCCESS:
                    binding.factoryLoading.hide();
                    break;
                case ERROR:
                    warningDialog(getContext(), getString(R.string.network_issue));
                    binding.factoryLoading.hide();
                    break;
            }
        });
    }

    private void observeGettingFactoriesList() {
        viewModel.getFactoriesList().observe(getViewLifecycleOwner(),response->{
            if (response!=null){
                String statusMessage = response.getResponseStatus().getStatusMessage();
                if (response.getResponseStatus().getIsSuccess()){
                    factoriesList = response.getFactories();
                    factoriesAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item,factoriesList);
                    binding.factorySpinner.setAdapter(factoriesAdapter);
                } else warningDialog(getContext(),statusMessage);
            }
        });
    }

    private ArrayAdapter<Factory> factoriesAdapter;
    private List<Factory> factoriesList = new ArrayList<>();
    private int selectedFactoryId = -1;
    private void setUpFactoriesListSpinner() {
//        factoriesAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item,factoriesList);
//        binding.factorySpinner.setAdapter(factoriesAdapter);
        binding.factorySpinner.setOnItemClickListener((parent, view, position, id) -> {
            selectedFactoryId = factoriesList.get(position).getFactoryId();
            viewModel.GetWorkCentersList(selectedFactoryId);
            clearWorkCenter();
            clearWorkCenterLines();
            clearWorkCenterSubLines();
            clearMachineFamily();
            clearMachine();
            clearStation();
            clearDie();
            clearJig();
        });
    }

    private void handleMachineOrStationRadioGroup() {
        binding.machineOrStation.setOnCheckedChangeListener((group, checkedId) -> {
             switch (checkedId){
                 case R.id.machine_option:
                     showMachine();
                     hideStation();
                     if (selectedWorkCenterSubLineId!=-1)
                        viewModel.GetMachineFamilyList(selectedWorkCenterSubLineId);
                     clearMachineFamily();
                     clearMachine();
                     clearStation();
                     clearDie();
                     clearJig();
                     break;
                 case R.id.station_option:
                     hideMachine();
                     showStation();
                     if (selectedWorkCenterSubLineId!=-1)
                         viewModel.GetStationList(selectedWorkCenterSubLineId);
                     clearMachineFamily();
                     clearMachine();
                     clearStation();
                     clearDie();
                     clearJig();
                     break;
             }
        });
    }

    private void showStation() {
        binding.stationSpinner.setText("");
        binding.station.setVisibility(View.VISIBLE);
    }

    private void hideStation(){
        binding.stationSpinner.setText("");
        binding.station.setVisibility(View.GONE);
    }

    private void hideMachine() {
        binding.machineSpinner.setText("");
        binding.machine.setVisibility(View.GONE);
        binding.machineFamilySpinner.setText("");
        binding.machineFamily.setVisibility(View.GONE);
        binding.die.setVisibility(View.GONE);
        binding.dieSpinner.setText("");
        binding.jig.setVisibility(View.GONE);
        binding.jigSpinner.setText("");
    }

    private void showMachine() {
        binding.machineSpinner.setText("");
        binding.machine.setVisibility(View.VISIBLE);
        binding.machineFamilySpinner.setText("");
        binding.machineFamily.setVisibility(View.VISIBLE);
        binding.die.setVisibility(View.VISIBLE);
        binding.dieSpinner.setText("");
        binding.jig.setVisibility(View.VISIBLE);
        binding.jigSpinner.setText("");
    }


    private void observeGettingStationsListStatus() {
        viewModel.getStationsListStatus().observe(getViewLifecycleOwner(),status -> {
            switch (status) {
                case LOADING:
                    binding.stationLoading.show();
                    break;
                case SUCCESS:
                    binding.stationLoading.hide();
                    break;
                case ERROR:
                    warningDialog(getContext(), getString(R.string.network_issue));
                    binding.stationLoading.hide();
                    break;
            }
        });
    }

    private void observeGettingStationsList() {
        viewModel.getStationsList().observe(getViewLifecycleOwner(),response->{
            if (response!=null){
                String statusMessage = response.getResponseStatus().getStatusMessage();
                if (response.getResponseStatus().getIsSuccess()){
                    stationsList = response.getProductionStations();
                    stationsAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item,stationsList);
                    binding.stationSpinner.setAdapter(stationsAdapter);
                } else warningDialog(getContext(),statusMessage);
            }
        });
    }

    private ArrayAdapter<Station> stationsAdapter;
    private List<Station> stationsList = new ArrayList<>();
    private int selectedStationId = -1;
    private void setUpStationsSpinner() {
//        stationsAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item,stationsList);
//        binding.stationSpinner.setAdapter(stationsAdapter);
        binding.stationSpinner.setOnItemClickListener((parent, view, position, id) -> {
            selectedStationId = stationsList.get(position).getProductionStationId();
        });
    }
    private void clearStation(){
        stationsList.clear();
        binding.stationSpinner.setText("");
        selectedStationId=-1;
    }

    private void attachButtonsToListeners() {
        binding.addStoppage.setOnClickListener(this);
        binding.stoppageStartTimeEdit.setOnClickListener(this);
        binding.stoppageStartDateEdit.setOnClickListener(this);
        binding.workCenterClear.setOnClickListener(this);
        binding.lineClear.setOnClickListener(this);
        binding.subLineClear.setOnClickListener(this);
        binding.machineFamilyClear.setOnClickListener(this);
        binding.machineClear.setOnClickListener(this);
        binding.dieClear.setOnClickListener(this);
        binding.jigClear.setOnClickListener(this);
        binding.stationClear.setOnClickListener(this);
    }

    private void observeGettingMachinesListStatus() {
        viewModel.getMachineListStatus().observe(getViewLifecycleOwner(),status -> {
            switch (status) {
                case LOADING:
                    binding.machineLoading.show();
                    break;
                case SUCCESS:
                    binding.machineLoading.hide();
                    break;
                case ERROR:
                    warningDialog(getContext(), getString(R.string.network_issue));
                    binding.machineLoading.hide();
                    break;
            }
        });
    }

    private void observeGettingMachinesList() {
        viewModel.getMachineList().observe(getViewLifecycleOwner(),response->{
            if (response!=null){
                String statusMessage = response.getResponseStatus().getStatusMessage();
                if (response.getResponseStatus().getIsSuccess()){
                    machinesList = response.getMachines();
                    machinesAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item,machinesList);
                    binding.machineSpinner.setAdapter(machinesAdapter);
                } else warningDialog(getContext(),statusMessage);
            }
        });
    }

    private ArrayAdapter<Machine> machinesAdapter;
    private List<Machine> machinesList = new ArrayList<>();
    private int selectedMachineId = -1;
    private void setUpMachinesSpinner() {
//        machinesAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item,machinesList);
//        binding.machineSpinner.setAdapter(machinesAdapter);
        binding.machineSpinner.setOnItemClickListener((parent, view, position, id) -> {
            selectedMachineId = machinesList.get(position).getMachineId();
        });
    }
    private void clearMachine(){
        machinesList.clear();
        binding.machineSpinner.setText("");
        selectedMachineId=-1;
    }

    private void observeGettingMachineFamilyListStatus() {
        viewModel.getMachineFamilyListStatus().observe(getViewLifecycleOwner(),status -> {
            switch (status) {
                case LOADING:
                    binding.machineFamilyLoading.show();
                    break;
                case SUCCESS:
                    binding.machineFamilyLoading.hide();
                    break;
                case ERROR:
                    warningDialog(getContext(), getString(R.string.network_issue));
                    binding.machineFamilyLoading.hide();
                    break;
            }
        });
    }

    private void observeGettingMachineFamilyList() {
        viewModel.getMachineFamilyList().observe(getViewLifecycleOwner(),response->{
            if (response!=null){
                String statusMessage = response.getResponseStatus().getStatusMessage();
                if (response.getResponseStatus().getIsSuccess()){
                    machineFamilyList = response.getMachineFamily();
                    machineFamilyAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item,machineFamilyList);
                    binding.machineFamilySpinner.setAdapter(machineFamilyAdapter);
                } else warningDialog(getContext(),statusMessage);
            }
        });
    }

    private ArrayAdapter<MachineFamily> machineFamilyAdapter;
    private List<MachineFamily> machineFamilyList = new ArrayList<>();
    private int selectedMachineFamilyId = -1;
    private void setMachineFamilySpinner() {
//        machineFamilyAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item,machineFamilyList);
//        binding.machineFamilySpinner.setAdapter(machineFamilyAdapter);
        binding.machineFamilySpinner.setOnItemClickListener((parent, view, position, id) -> {
            selectedMachineFamilyId = machineFamilyList.get(position).getMachineFamilyId();
            clearMachine();
            clearStation();
            clearDie();
            clearJig();
            viewModel.GetMachineList(selectedMachineFamilyId);
        });
    }
    private void clearMachineFamily (){
        machineFamilyList.clear();
        binding.machineFamilySpinner.setText("");
        selectedMachineFamilyId=-1;
    }

    private void observeGettingWorkCenterSubLinesListStatus() {
        viewModel.getWorkCenterSubLinesListStatus().observe(getViewLifecycleOwner(),status -> {
            switch (status) {
                case LOADING:
                    binding.subLineLoading.show();
                    break;
                case SUCCESS:
                    binding.subLineLoading.hide();
                    break;
                case ERROR:
                    warningDialog(getContext(), getString(R.string.network_issue));
                    binding.subLineLoading.hide();
                    break;
            }
        });
    }

    private void observeGettingWorkCenterSubLinesList() {
        viewModel.getWorkCenterSubLinesList().observe(getViewLifecycleOwner(),response->{
            if (response!=null){
                String statusMessage = response.getResponseStatus().getStatusMessage();
                if (response.getResponseStatus().getIsSuccess()){
                    workCenterSubLinesList = response.getWorkCenterSubLines();
                    workCenterSubLineAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item,workCenterSubLinesList);
                    binding.sublineSpinner.setAdapter(workCenterSubLineAdapter);
                } else warningDialog(getContext(),statusMessage);
            }
        });
    }

    private ArrayAdapter<WorkCenterSubLine> workCenterSubLineAdapter;
    private List<WorkCenterSubLine> workCenterSubLinesList = new ArrayList<>();
    private int selectedWorkCenterSubLineId = -1;
    private void setUpWorkCenterSubLinesSpinner() {
//        workCenterSubLineAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item,workCenterSubLinesList);
//        binding.sublineSpinner.setAdapter(workCenterSubLineAdapter);
        binding.sublineSpinner.setOnItemClickListener((parent, view, position, id) -> {
            selectedWorkCenterSubLineId = workCenterSubLinesList.get(position).getProductionSubLineId();
            clearMachineFamily();
            clearMachine();
            clearStation();
            clearDie();
            clearJig();
            viewModel.GetMachineFamilyList(selectedWorkCenterSubLineId);
        });
    }
    private void clearWorkCenterSubLines (){
        workCenterSubLinesList.clear();
        binding.sublineSpinner.setText("");
        selectedWorkCenterSubLineId=-1;
    }

    private void observeGettingWorkCenterLinesListStatus() {
        viewModel.getWorkCenterLinesListStatus().observe(getViewLifecycleOwner(),status -> {
            switch (status) {
                case LOADING:
                    binding.lineLoading.show();
                    break;
                case SUCCESS:
                    binding.lineLoading.hide();
                    break;
                case ERROR:
                    warningDialog(getContext(), getString(R.string.network_issue));
                    binding.lineLoading.hide();
                    break;
            }
        });
    }

    private void observeGettingWorkCenterLinesList() {
        viewModel.getWorkCenterLinesList().observe(getViewLifecycleOwner(),response->{
            if (response!=null){
                String statusMessage = response.getResponseStatus().getStatusMessage();
                if (response.getResponseStatus().getIsSuccess()){
                    workCenterLinesList = response.getWorkCenterLines();
                    workCenterLineAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item,workCenterLinesList);
                    binding.lineSpinner.setAdapter(workCenterLineAdapter);
                } else warningDialog(getContext(),statusMessage);
            }
        });
    }

    private ArrayAdapter<WorkCenterLine> workCenterLineAdapter;
    private List<WorkCenterLine> workCenterLinesList = new ArrayList<>();
    private int selectedWorkCenterLineId = -1;
    private void setUpWorkCenterLinesSpinner() {
//        workCenterLineAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item,workCenterLinesList);
//        binding.lineSpinner.setAdapter(workCenterLineAdapter);
        binding.lineSpinner.setOnItemClickListener((parent, view, position, id) -> {
            selectedWorkCenterLineId = workCenterLinesList.get(position).getProductionLineId();
            clearWorkCenterSubLines();
            clearMachineFamily();
            clearMachine();
            clearStation();
            clearDie();
            clearJig();
            viewModel.GetWorkCenterSubLinesList(selectedWorkCenterLineId);
        });
    }
    private void clearWorkCenterLines (){
        workCenterLinesList.clear();
        binding.lineSpinner.setText("");
        selectedWorkCenterLineId=-1;
    }

    private void observeGettingWorkCentersListStatus() {
        viewModel.getWorkCentersListStatus().observe(getViewLifecycleOwner(),status -> {
            switch (status) {
                case LOADING:
                    binding.workCenterLoading.show();
                    break;
                case SUCCESS:
                    binding.workCenterLoading.hide();
                    break;
                case ERROR:
                    warningDialog(getContext(), getString(R.string.network_issue));
                    binding.workCenterLoading.hide();
                    break;
            }
        });
    }

    private void observeGettingWorkCentersList() {
        viewModel.getWorkCentersList().observe(getViewLifecycleOwner(),response ->{
            if (response!=null){
                String statusMessage = response.getResponseStatus().getStatusMessage();
                if (response.getResponseStatus().getIsSuccess()){
                    workCentersList = response.getWorkCenters();
                    workCenterAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item,workCentersList);
                    binding.workCenterSpinner.setAdapter(workCenterAdapter);
                } else warningDialog(getContext(),statusMessage);
            }
        });
    }

    private ArrayAdapter<WorkCenter> workCenterAdapter;
    private List<WorkCenter> workCentersList = new ArrayList<>();
    private int selectedWorkCenterId = -1;
    private void setUpWorkCentersSpinner() {
//        workCenterAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item,workCentersList);
//        binding.workCenterSpinner.setAdapter(workCenterAdapter);
        binding.workCenterSpinner.setOnItemClickListener((parent, view, position, id) -> {
            selectedWorkCenterId = workCentersList.get(position).getWorkCenterId();
            clearWorkCenterLines();
            clearWorkCenterSubLines();
            clearMachineFamily();
            clearMachine();
            clearStation();
            clearDie();
            clearJig();
            viewModel.GetWorkCenterLinesList(selectedWorkCenterId);
        });
    }
    private void clearWorkCenter (){
        workCentersList.clear();
        binding.workCenterSpinner.setText("");
        selectedWorkCenterId=-1;
    }

    private void observeGettingStoppageNamesStatus() {
        viewModel.getStoppagesNamesListStatus().observe(getViewLifecycleOwner(),status -> {
                switch (status) {
                    case LOADING:
                        binding.stoppageNameLoading.show();
                        break;
                    case SUCCESS:
                        binding.stoppageNameLoading.hide();
                        break;
                    case ERROR:
                        warningDialog(getContext(), getString(R.string.network_issue));
                        binding.stoppageNameLoading.hide();
                        break;
                }
        });
    }

    private void observeGettingStoppageNames() {
        viewModel.getStoppagesNamesList().observe(getViewLifecycleOwner(),response ->{
            if (response!=null){
                String statusMessage = response.getResponseStatus().getStatusMessage();
                if (response.getResponseStatus().getIsSuccess()){
                    stoppageNameList = response.getStoppagesNames();
                    stoppageNameAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item,stoppageNameList);
                    binding.stoppageNameSpinner.setAdapter(stoppageNameAdapter);
                } else warningDialog(getContext(),statusMessage);
            }
        });
    }

    private ArrayAdapter<StoppageName> stoppageNameAdapter;
    private List<StoppageName> stoppageNameList = new ArrayList<>();
    private int selectedStoppageNameId = -1;
    private void setUpStoppageNamesSpinner() {
//        stoppageNameAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item,stoppageNameList);
//        binding.stoppageNameSpinner.setAdapter(stoppageNameAdapter);
        binding.stoppageNameSpinner.setOnItemClickListener((parent, view, position, id) -> {
            selectedStoppageNameId = stoppageNameList.get(position).getStoppagesNameId();
        });
    }
    private void clearStoppageName (){
        stoppageNameList.clear();
        binding.stoppageNameSpinner.setText("");
        selectedStoppageNameId=-1;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_stoppage:
                if (selectedStoppageNameId!=-1){
                    if (!stoppageDate.isEmpty()){
                        if (selectedFactoryId!=-1){
                            AddStoppageData data = new AddStoppageData(
                                    USER_ID,
                                    DEVICE_SERIAL_NO,
                                    selectedFactoryId,
                                    selectedWorkCenterId,
                                    selectedWorkCenterLineId,
                                    selectedWorkCenterSubLineId,
                                    binding.machineOption.isChecked(),
                                    selectedMachineFamilyId,
                                    selectedMachineId,
                                    selectedStationId,
                                    selectedDieId,
                                    selectedJigId,
                                    selectedStoppageNameId,
                                    stoppageDate,
                                    stoppageDate+" "+stoppageTime,
                                    LocaleHelper.getLanguage(getContext())
                            );
                            viewModel.AddStoppage(data);
                        } else warningDialog(getContext(),getString(R.string.please_select_factory));
                    } else warningDialog(getContext(),getString(R.string.please_enter_stoppage_start_date_and_time));
                } else warningDialog(getContext(),getString(R.string.please_select_stopage_name));
                break;
            case R.id.stoppage_start_date_edit:
                dpd.show(getChildFragmentManager(),"hgchg");
                break;
            case R.id.stoppage_start_time_edit:
                Calendar calendar = Calendar.getInstance();
                new TimePickerDialog(getContext(),
                        (view, hourOfDay, minute) -> {
                            String amPm;
                            int hour;
                            if (hourOfDay>12) {
                                hour = hourOfDay - 12;
                                amPm = "pm";
                            } else if (hourOfDay<12&&hourOfDay>0){
                                hour = hourOfDay;
                                amPm = "am";
                            } else {
                                hour = 12;
                                amPm = "am";
                            }
                            stoppageTime = hourOfDay+":"+minute;
                            binding.stoppageStartTime.getEditText().setText(hour+":"+minute+" "+amPm);
                        },calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE),false).show();
                break;
            case R.id.work_center_clear:
            case R.id.station_clear:
            case R.id.machine_clear:
                clearWorkCenter();
                clearWorkCenterLines();
                clearWorkCenterSubLines();
                clearMachineFamily();
                clearMachine();
                clearStation();
                clearDie();
                clearJig();
                break;
            case R.id.line_clear:
                clearWorkCenterLines();
                clearWorkCenterSubLines();
                clearMachineFamily();
                clearMachine();
                clearStation();
                clearDie();
                clearJig();
                break;
            case R.id.sub_line_clear:
                clearWorkCenterSubLines();
                clearMachineFamily();
                clearMachine();
                clearStation();
                clearDie();
                clearJig();
                break;
            case R.id.machine_family_clear:
                clearMachineFamily();
                clearMachine();
                clearStation();
                clearDie();
                clearJig();
                break;
            case R.id.die_clear:
                clearDie();
                break;
            case R.id.jig_clear:
                clearJig();
                break;
        }
    }

    @Override
    public void onBarcodeEvent(BarcodeReadEvent barcodeReadEvent) {
        getActivity().runOnUiThread(()->{
            String scannedText = barCodeReader.scannedData(barcodeReadEvent);
            if (binding.machine.getVisibility() == View.VISIBLE){
                viewModel.GetMachineDetails(scannedText);
            } else {
                viewModel.GetStationDetails(scannedText);
            }
        });
    }

    @Override
    public void onFailureEvent(BarcodeFailureEvent barcodeFailureEvent) {

    }

    @Override
    public void onTriggerEvent(TriggerStateChangeEvent triggerStateChangeEvent) {
        barCodeReader.onTrigger(triggerStateChangeEvent);
    }

    @Override
    public void barcodeRead(com.intermec.aidc.BarcodeReadEvent barcodeReadEvent) {
        getActivity().runOnUiThread(()->{
            String scannedText = barCodeReaderInterMec.scannedData(barcodeReadEvent);
            if (binding.machine.getVisibility() == View.VISIBLE){
                viewModel.GetMachineDetails(scannedText);
            } else {
                viewModel.GetStationDetails(scannedText);
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        barCodeReader.onResume();
        MyMethods.changeTitle(getString(R.string.add_stoppage),(MainActivity) requireActivity());
    }

    @Override
    public void onPause() {
        super.onPause();
        barCodeReader.onPause();
        barCodeReaderInterMec.onPause();
    }
}