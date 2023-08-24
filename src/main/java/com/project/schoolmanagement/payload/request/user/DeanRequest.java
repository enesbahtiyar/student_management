package com.project.schoolmanagement.payload.request.user;

import com.project.schoolmanagement.payload.request.abstracts.BaseUserRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import net.bytebuddy.implementation.bind.annotation.Super;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class DeanRequest extends BaseUserRequest
{
    private boolean builtIn;
}
