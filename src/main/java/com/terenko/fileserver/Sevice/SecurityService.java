package com.terenko.fileserver.Sevice;

import com.terenko.fileserver.model.Catalog;
import com.terenko.fileserver.model.CustomUser;
import com.terenko.fileserver.model.File;
import com.terenko.fileserver.util.AccessMode;
import com.terenko.fileserver.util.AccessModificator;
import org.springframework.stereotype.Service;

@Service
public class SecurityService implements SecurityServiceInterface {
    @Override
    public AccessModificator getAccesssModificatorForFile(File file, CustomUser us) {
        return getAccesssModificatorForCatalog(file.getCatalog(),us);
}

    @Override
    public AccessModificator getAccesssModificatorForCatalog(Catalog catalog, CustomUser us) {
        if(catalog.getCreator().equals(us))
            return AccessModificator.CREATOR;
    if(catalog.getAccessMode()== AccessMode.PRIVATE) {
        if (catalog.getHaveAccess().contains(us))
            return AccessModificator.HaveACCESS;
    }else {
        return AccessModificator.HaveACCESS;
    }
     return AccessModificator.RESTRICTED;
    }
}
