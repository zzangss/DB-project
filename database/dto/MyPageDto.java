package database.dto;

import database.dao.*;
import database.model.*;

import java.sql.*;
import java.util.List;

/**
 * 마이페이지에 보여줄 DTO
 */
public class MyPageDto {
    private List<Team> teams;
    private List<RoleAssignment> roles;
    private List<Task> tasks;
    private List<Contribution> contributions;

    public List<Team> getTeams() { return teams; }
    public void setTeams(List<Team> teams) { this.teams = teams; }

    public List<RoleAssignment> getRoles() { return roles; }
    public void setRoles(List<RoleAssignment> roles) { this.roles = roles; }

    public List<Task> getTasks() { return tasks; }
    public void setTasks(List<Task> tasks) { this.tasks = tasks; }

    public List<Contribution> getContributions() { return contributions; }
    public void setContributions(List<Contribution> contributions) { this.contributions = contributions; }
}
