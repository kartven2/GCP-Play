/*-
Copyright [2020] Karthik.V

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package com.alpha.messaging.events.models;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.validation.constraints.*;

public class Events implements Serializable {

	private static final long serialVersionUID = 1L;
	@NotNull
	private String id;
	@NotNull
	private String name;
	private String description;
	@NotNull
	private String details;
	@NotNull
	private Timestamp startTime;
	private Timestamp endTime;
	private boolean open = true;

	public Events() {
	}

	public Events(String id, String name, String description, String details) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.details = details;
	}

	public Events(String id, String name, String details, Timestamp startTime) {
		super();
		this.id = id;
		this.name = name;
		this.details = details;
		this.startTime = startTime;
	}

	public Events(@NotNull String id, @NotNull String name, String description, @NotNull String details,
			@NotNull Timestamp startTime, Timestamp endTime, boolean open) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.details = details;
		this.startTime = startTime;
		this.endTime = endTime;
		this.open = open;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public Timestamp getStartTime() {
		return startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public Timestamp getEndTime() {
		return endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	@Override
	public String toString() {
		return "Events [id=" + id + ", name=" + name + ", description=" + description + ", details=" + details
				+ ", startTime=" + startTime + ", endTime=" + endTime + ", open=" + open + "]";
	}

}
