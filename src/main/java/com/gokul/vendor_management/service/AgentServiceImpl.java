package com.gokul.vendor_management.service;

import com.gokul.vendor_management.entity.Agent;
import com.gokul.vendor_management.exception.ResourceNotFoundException;
import com.gokul.vendor_management.repository.AgentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgentServiceImpl implements AgentService {

    private final AgentRepository agentRepository;

    public AgentServiceImpl(AgentRepository agentRepository) {
        this.agentRepository = agentRepository;
    }

    @Override
    public Agent createAgent(Agent agent) {
        return agentRepository.save(agent);
    }

    @Override
    public List<Agent> getAllAgents() {
        return agentRepository.findAll();
    }

    @Override
    public Agent getAgentById(Long id) {
        return agentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Agent not found with id: " + id));
    }

    @Override
    public Agent updateAgent(Long id, Agent updatedAgent) {
        Agent existing = getAgentById(id);
        existing.setName(updatedAgent.getName());
        existing.setMobileNumber(updatedAgent.getMobileNumber());
        return agentRepository.save(existing);
    }

    @Override
    public void deleteAgent(Long id) {
        Agent existing = getAgentById(id);
        agentRepository.delete(existing);
    }
}