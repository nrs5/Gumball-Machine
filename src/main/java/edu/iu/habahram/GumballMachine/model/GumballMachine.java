package edu.iu.habahram.GumballMachine.model;

public class GumballMachine implements IGumballMachine {
    final String SOLD_OUT = GumballMachineState.OUT_OF_GUMBALLS.name();
    final String NO_QUARTER = GumballMachineState.NO_QUARTER.name();
    final String HAS_QUARTER = GumballMachineState.HAS_QUARTER.name();
    final String SOLD = GumballMachineState.GUMBALL_SOLD.name();
    private String id;
    String state = SOLD_OUT;
    int count = 0;

    public GumballMachine(String id, String state, int count) {
        this.id = id;
        this.state = state;
        this.count = count;
    }

    @Override
    public TransitionResult insertQuarter() {
        boolean succeeded = false;
        String message = "";
        if (state.equalsIgnoreCase(HAS_QUARTER)) {
            message = "You can't insert another quarter";
        } else if (state.equalsIgnoreCase(NO_QUARTER)) {
            state = HAS_QUARTER;
            message = "You inserted a quarter";
            succeeded = true;
        } else if (state.equalsIgnoreCase(SOLD_OUT)) {
            message = "You can't insert a quarter, the machine is sold out";
        } else if (state.equalsIgnoreCase(SOLD)) {
            message = "Please wait, we're already giving you a gumball";
        }
        return new TransitionResult(succeeded, message, state, count);
    }

    @Override
    public TransitionResult ejectQuarter() {
        //TODO
        boolean succeeded = false;
        String message = "";
        if (state.equalsIgnoreCase(HAS_QUARTER)) {
            message = "Ejecting quarter";
            succeeded = true;
        } else if (state.equalsIgnoreCase(NO_QUARTER)) {
            message = "No quarter to eject!";
        } else if (state.equalsIgnoreCase(SOLD_OUT)) {
            message = "The machine is sold out";
        } else if (state.equalsIgnoreCase(SOLD)) {
            message = "Ejecting";
            succeeded = true;
        }
        return new TransitionResult(succeeded, message, state, count);
    }

    @Override
    public TransitionResult turnCrank() {
        //TODO
        boolean succeeded = false;
        String message = "";
        String stateAfterAttempt = null;
        if (state.equalsIgnoreCase(HAS_QUARTER)) {
            message = "turning crank";
            state = SOLD;
            return dispense();
        } else if (state.equalsIgnoreCase(NO_QUARTER)) {
            message = "No quarter";
        } else if (state.equalsIgnoreCase(SOLD_OUT)) {
            message = "The machine is sold out";
        } else if (state.equalsIgnoreCase(SOLD)) {
            message = "Turning twice doesn't get you another gumball";
        }
        stateAfterAttempt = state;
        return new TransitionResult(succeeded, message, state, count);
    }


    @Override
    public TransitionResult dispense() {
        boolean succeeded = false;
        String message = "";
        String stateAfterAttempt = null;
        if(state.equalsIgnoreCase(SOLD)){
            message = "Gumball comes rolling out of the slot";
            count = count - 1;
            if (count == 0){
                message = "Out of gumballs!";
                state = SOLD_OUT;
            } else {
                state = NO_QUARTER;
            }
            succeeded = true;
        } else if (state.equalsIgnoreCase(NO_QUARTER)){
            message = "Pay first!";
        } else if (state.equalsIgnoreCase(SOLD_OUT)){
            message = "No gumball dispensed";
        } else if (state.equalsIgnoreCase(HAS_QUARTER)){
            message = "no gumball dispensed";
        }
        stateAfterAttempt = state;
        return new TransitionResult(succeeded, message, state, count);
    }

    @Override
    public void changeTheStateTo(GumballMachineState name) {

    }

    @Override
    public Integer getCount() {
        return count;
    }

    @Override
    public String getTheStateName() {
        return null;
    }

    @Override
    public void releaseBall() {

    }


}