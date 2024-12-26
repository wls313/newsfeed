package com.fiveman.newsfeed.board.dto;

import java.time.LocalDate;

public record BoardfilterRequestDto(String title, String sort, String startDate, String endDate) {
}
