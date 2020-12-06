package com.terenko.fileserver.Sevice;

import com.terenko.fileserver.model.Catalog;
import com.terenko.fileserver.model.CustomUser;
import com.terenko.fileserver.model.File;
import com.terenko.fileserver.util.AccessModificator;

public interface SecurityServiceInterface<T> {
    AccessModificator getAccesssModificatorForFile(File file, CustomUser us);
    AccessModificator getAccesssModificatorForCatalog(Catalog catalog, CustomUser us);
}
