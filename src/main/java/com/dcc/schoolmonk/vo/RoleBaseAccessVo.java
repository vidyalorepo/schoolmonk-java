package com.dcc.schoolmonk.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "ROLE_BASE_ACCESS")
@EntityListeners(AuditingEntityListener.class)
public class RoleBaseAccessVo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "role_base_access_id", unique = true, nullable = false, updatable = false)
	private long roleBaseAccessId;

	@Column(name = "role_name")
	private String roleName;

	@Column(name = "role_id")
	private long roleId;

//	@Column(columnDefinition="tinyint(1) default 1")
//	private boolean include;

	@Column(name = "bus_booking", columnDefinition = "tinyint(1) default 0")
	private boolean busBooking;

	@Column(name = "bus_booking_admin_panel", columnDefinition = "tinyint(1) default 0")
	private boolean busBookingAdminPanel;

	@Column(name = "conference_room_booking", columnDefinition = "tinyint(1) default 0")
	private boolean conferenceRoomBooking;

	@Column(name = "conference_room_booking_admin_panel", columnDefinition = "tinyint(1) default 0")
	private boolean conferenceRoomBookingAdminPanel;

	@Column(name = "meal_booking", columnDefinition = "tinyint(1) default 0")
	private boolean mealBooking;

	@Column(name = "meal_booking_admin_panel", columnDefinition = "tinyint(1) default 0")
	private boolean mealBookingAdminPanel;

	@Column(name = "meal_booking_menu_chart", columnDefinition = "tinyint(1) default 0")
	private boolean mealBookingMenuChart;

	@Column(name = "meal_booking_menu_enter_button", columnDefinition = "tinyint(1) default 0")
	private boolean mealBookingMenuEnterButton;

	@Column(name = "plant_conference_room_booking", columnDefinition = "tinyint(1) default 0")
	private boolean plantConferenceRoomBooking;

	@Column(name = "plant_conference_room_booking_admin_panel", columnDefinition = "tinyint(1) default 0")
	private boolean plantConferenceRoomBookingAdminPanel;

	@Column(name = "recreation_hall_booking", columnDefinition = "tinyint(1) default 0")
	private boolean recreationHallBooking;

	public long getRoleBaseAccessId() {
		return roleBaseAccessId;
	}

	public void setRoleBaseAccessId(long roleBaseAccessId) {
		this.roleBaseAccessId = roleBaseAccessId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public boolean isBusBooking() {
		return busBooking;
	}

	public void setBusBooking(boolean busBooking) {
		this.busBooking = busBooking;
	}

	public boolean isBusBookingAdminPanel() {
		return busBookingAdminPanel;
	}

	public void setBusBookingAdminPanel(boolean busBookingAdminPanel) {
		this.busBookingAdminPanel = busBookingAdminPanel;
	}

	public boolean isConferenceRoomBooking() {
		return conferenceRoomBooking;
	}

	public void setConferenceRoomBooking(boolean conferenceRoomBooking) {
		this.conferenceRoomBooking = conferenceRoomBooking;
	}

	public boolean isConferenceRoomBookingAdminPanel() {
		return conferenceRoomBookingAdminPanel;
	}

	public void setConferenceRoomBookingAdminPanel(boolean conferenceRoomBookingAdminPanel) {
		this.conferenceRoomBookingAdminPanel = conferenceRoomBookingAdminPanel;
	}

	public boolean isMealBooking() {
		return mealBooking;
	}

	public void setMealBooking(boolean mealBooking) {
		this.mealBooking = mealBooking;
	}

	public boolean isMealBookingAdminPanel() {
		return mealBookingAdminPanel;
	}

	public void setMealBookingAdminPanel(boolean mealBookingAdminPanel) {
		this.mealBookingAdminPanel = mealBookingAdminPanel;
	}

	public boolean isMealBookingMenuChart() {
		return mealBookingMenuChart;
	}

	public void setMealBookingMenuChart(boolean mealBookingMenuChart) {
		this.mealBookingMenuChart = mealBookingMenuChart;
	}

	public boolean isMealBookingMenuEnterButton() {
		return mealBookingMenuEnterButton;
	}

	public void setMealBookingMenuEnterButton(boolean mealBookingMenuEnterButton) {
		this.mealBookingMenuEnterButton = mealBookingMenuEnterButton;
	}

	public boolean isPlantConferenceRoomBooking() {
		return plantConferenceRoomBooking;
	}

	public void setPlantConferenceRoomBooking(boolean plantConferenceRoomBooking) {
		this.plantConferenceRoomBooking = plantConferenceRoomBooking;
	}

	public boolean isPlantConferenceRoomBookingAdminPanel() {
		return plantConferenceRoomBookingAdminPanel;
	}

	public void setPlantConferenceRoomBookingAdminPanel(boolean plantConferenceRoomBookingAdminPanel) {
		this.plantConferenceRoomBookingAdminPanel = plantConferenceRoomBookingAdminPanel;
	}

	public boolean isRecreationHallBooking() {
		return recreationHallBooking;
	}

	public void setRecreationHallBooking(boolean recreationHallBooking) {
		this.recreationHallBooking = recreationHallBooking;
	}

}
