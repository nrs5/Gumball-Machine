package edu.iu.habahram.GumballMachine.service;

import edu.iu.habahram.GumballMachine.model.*;
import edu.iu.habahram.GumballMachine.repository.IGumballRepository;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class GumballService implements IGumballService{

    IGumballRepository gumballRepository;

    public GumballService(IGumballRepository gumballRepository) {
        this.gumballRepository = gumballRepository;
    }

    @Override
    public TransitionResult insertQuarter(String id) throws IOException {
        return transition(id, "insertQuarter");

    }

    @Override
    public TransitionResult ejectQuarter(String id) throws IOException {

        return transition(id, "ejectQuarter");
    }

    @Override
    public TransitionResult turnCrank(String id) throws IOException {

        return transition(id, "turnCrank");
    }


    public TransitionResult transition(String id, String transition) throws IOException {
        GumballMachineRecord record = gumballRepository.findById(id);
        IGumballMachine machine = new GumballMachine2(record.getId(), record.getState(), record.getCount());
        TransitionResult result = null;

        if (transition.equals("turnCrank")) {
            result = machine.turnCrank();
        } else if (transition.equals("ejectQuarter")) {
            result = machine.ejectQuarter();
        } else if (transition.equals("insertQuarter")) {
            result = machine.insertQuarter();
        }
        if(result.succeeded()) {
            record.setState(result.stateAfter());
            record.setCount(result.countAfter());
            save(record);
        }
        return result;

    }




    @Override
    public TransitionResult dispense(String id) throws IOException {

        GumballMachineRecord record = gumballRepository.findById(id);
        IGumballMachine machine = new GumballMachine2(record.getId(), record.getState(), record.getCount());
        TransitionResult result = machine.dispense();
        if(result.succeeded()) {
            record.setState(result.stateAfter());
            record.setCount(result.countAfter());
            save(record);
        }
        return result;
    }

    @Override
    public TransitionResult refill(String id, int count) throws IOException {
        GumballMachineRecord record = gumballRepository.findById(id);
        IGumballMachine machine = new GumballMachine2(record.getId(), record.getState(), record.getCount());
        TransitionResult result = machine.refill();
        if(result.succeeded()) {
            record.setState(result.stateAfter());
            record.setCount(count);
            save(record);
        }
        return result;
    }
    

    @Override
    public List<GumballMachineRecord> findAll() throws IOException {
        return gumballRepository.findAll();
    }

    @Override
    public GumballMachineRecord findById(String id) throws IOException {
        return gumballRepository.findById(id);
    }

    @Override
    public String save(GumballMachineRecord gumballMachineRecord) throws IOException {
        return gumballRepository.save(gumballMachineRecord);
    }
}
