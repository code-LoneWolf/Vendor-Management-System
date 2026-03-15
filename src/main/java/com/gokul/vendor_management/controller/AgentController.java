package com.gokul.vendor_management.controller;

import com.gokul.vendor_management.entity.Agent;
import com.gokul.vendor_management.service.AgentService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agents")
public class AgentController {

    private final AgentService agentService;

    public AgentController(AgentService agentService) {
        this.agentService = agentService;
    }

    @PostMapping
    public Agent createAgent(@Valid @RequestBody Agent agent) {
        return agentService.createAgent(agent);
    }

    @GetMapping
    public List<Agent> getAllAgents() {
        return agentService.getAllAgents();
    }

    @GetMapping("/{id}")
    public Agent getAgentById(@PathVariable Long id) {
        return agentService.getAgentById(id);
    }

    @PutMapping("/{id}")
    public Agent updateAgent(@PathVariable Long id,
                             @Valid @RequestBody Agent agent) {
        return agentService.updateAgent(id, agent);
    }

    @DeleteMapping("/{id}")
    public String deleteAgent(@PathVariable Long id) {
        agentService.deleteAgent(id);
        return "Agent deleted successfully with id: " + id;
    }
}