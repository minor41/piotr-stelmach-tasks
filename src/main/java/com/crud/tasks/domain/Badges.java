package com.crud.tasks.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Badges {

    @JsonProperty("votes")
    private int votes;

    @JsonProperty("attachments")
    private AttachmentByType attachments;
}