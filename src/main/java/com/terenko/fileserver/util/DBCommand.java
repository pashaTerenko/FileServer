package com.terenko.fileserver.util;

import org.springframework.data.jpa.repository.JpaRepository;

import java.io.IOException;

public interface DBCommand {
     void execute() throws IOException;
     DBCommand setRepository(JpaRepository repository);
}
