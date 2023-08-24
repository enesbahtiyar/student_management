package com.project.schoolmanagement.payload.response.user;

import com.project.schoolmanagement.payload.response.abstracts.BaseUserResponse;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import net.bytebuddy.implementation.bind.annotation.Super;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class DeanResponse extends BaseUserResponse
{
    private boolean builtIn;
}
