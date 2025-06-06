package DB2025Team08.dto;

import java.sql.*;
import java.util.List;

import DB2025Team08.dao.*;
import DB2025Team08.model.*;

/**
 * 마이페이지에 표시할 데이터를 담는 DTO 클래스.
 */
public class MyPageDto {
    /**
     * 사용자가 속한 팀 목록
     */
    private List<Team> teams;

    /**
     * 사용자의 역할 할당 목록
     */
    private List<RoleAssignment> roles;

    /**
     * 사용자가 맡은 과제 목록
     */
    private List<Task> tasks;

    /**
     * 사용자의 기여도 목록
     */
    private List<Contribution> contributions;

    /**
     * 팀 목록을 반환한다.
     *
     * @return 팀 목록
     */
    public List<Team> getTeams() { return teams; }

    /**
     * 팀 목록을 설정한다.
     *
     * @param teams 팀 목록
     */
    public void setTeams(List<Team> teams) { this.teams = teams; }

    /**
     * 역할 할당 목록을 반환한다.
     *
     * @return 역할 할당 목록
     */
    public List<RoleAssignment> getRoles() { return roles; }

    /**
     * 역할 할당 목록을 설정한다.
     *
     * @param roles 역할 할당 목록
     */
    public void setRoles(List<RoleAssignment> roles) { this.roles = roles; }

    /**
     * 과제 목록을 반환한다.
     *
     * @return 과제 목록
     */
    public List<Task> getTasks() { return tasks; }

    /**
     * 과제 목록을 설정한다.
     *
     * @param tasks 과제 목록
     */
    public void setTasks(List<Task> tasks) { this.tasks = tasks; }

    /**
     * 기여도 목록을 반환한다.
     *
     * @return 기여도 목록
     */
    public List<Contribution> getContributions() { return contributions; }

    /**
     * 기여도 목록을 설정한다.
     *
     * @param contributions 기여도 목록
     */
    public void setContributions(List<Contribution> contributions) { this.contributions = contributions; }
}
