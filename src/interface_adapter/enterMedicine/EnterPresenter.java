package interface_adapter.enterMedicine;

import interface_adapter.TableState;
import interface_adapter.TableViewModel;
import interface_adapter.checklistChecked.ChecklistViewModel;
import use_case.enterMedicine.EnterOutputBoundary;
import use_case.enterMedicine.EnterOutputData;

public class EnterPresenter implements EnterOutputBoundary {

    private EnterViewModel enterViewModel;
    private ChecklistViewModel checklistViewModel;
    private TableViewModel tableViewModel;

    public EnterPresenter(EnterViewModel enterViewModel, ChecklistViewModel
            checklistViewModel, TableViewModel tableViewModel){
        this.enterViewModel = enterViewModel;
        this.checklistViewModel = checklistViewModel;
        this.tableViewModel = tableViewModel;
    }

    @Override
    public void prepareSuccessView(EnterOutputData entry){

        TableState tableState = tableViewModel.getState();
        String[] tableData = new String[]{entry.getMedication(),
                entry.getDose(), entry.getInventory(), String.valueOf(entry.getSu()), String.valueOf(entry.getM()),
                String.valueOf(entry.getTu()), String.valueOf(entry.getW()), String.valueOf(entry.getTh()),
                String.valueOf(entry.getF()), String.valueOf(entry.getSa()), entry.getDescription()};
        tableState.addData(tableData);

        this.tableViewModel.setState(tableState);
        tableViewModel.firePropertyChanged();

    }

    public void updateChecklistView(EnterOutputData entry){
        String[] checklistAddition = new String[]{entry.getMedication(), entry.getDose()};
        checklistViewModel.firePropertyChangedAddTake(checklistAddition);
    }

    @Override
    public void updateLowView(EnterOutputData enterOutputData) {
        String[] lowAddition = new String[]{enterOutputData.getMedication(), String.valueOf(enterOutputData.getDosesRemaining())};
        if (enterOutputData.getDosesRemaining() == 0) {
            checklistViewModel.addRestock(enterOutputData.getMedication());
        } checklistViewModel.firePropertyChangedAddLow(lowAddition);
    }

    @Override
    public void preparePopUp(String message){
        EnterState enterState = enterViewModel.getState();
        enterState.setMessage(message);
        enterViewModel.firePropertyChanged();
    }
}
