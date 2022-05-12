package com.xyram.ticketingTool.service;

import com.xyram.ticketingTool.apiresponses.ApiResponse;
import com.xyram.ticketingTool.entity.Position;

public interface PositionService {
	ApiResponse addposition(Position  position);
}
