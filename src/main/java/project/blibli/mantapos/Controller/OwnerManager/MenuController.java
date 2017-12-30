package project.blibli.mantapos.Controller.OwnerManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import project.blibli.mantapos.Model.Menu;
import project.blibli.mantapos.Service.OwnerManager.MenuService;

@RestController
public class MenuController {

    MenuService menuService;

    @Autowired
    public MenuController (MenuService menuService){
        this.menuService = menuService;
    }

    //Jika user mengakses menu/{page}, dimana {page} ini adalah page keberapa laman menu itu, misal menu/1 berarti laman menu page 1 di pagination-nya
    @GetMapping(value = "/menu/{page}")
    public ModelAndView menuPaginated(Menu menu, @PathVariable("page") int page, Authentication authentication){
        return menuService.getMappingMenu(authentication, page);
    }

    //Jika user menambahkan menu baru
    @PostMapping(value = "/menu", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView addMenuJson(@ModelAttribute("menu") Menu menu,
                                    Authentication authentication){
        return menuService.postMappingAddNewMenu(authentication, menu);
    }

    //jika user menghapus menu
    @GetMapping(value = "/delete/menu/{id}")
    public ModelAndView deleteMenu(@PathVariable("id") int id){
        return menuService.getMappingDeleteMenu(id);
    }

    //jika user mengakses halaman untuk mengedit menu (keluar form dengan value dari menu yg bersesuaian di set ke input2 yang ada
    @GetMapping(value = "/edit/menu/{id}", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView editMenuHtml(@PathVariable("id") int id){
        return menuService.getMappingEditMenu(id);
    }

    //jika user posting menu yang sudah di edit
    @PostMapping(value = "/edit-menu", produces = MediaType.TEXT_HTML_VALUE)
    public ModelAndView editMenuPostHtml(@ModelAttribute("menu") Menu menu,
                                         Authentication authentication){
        return menuService.postMappingEditMenu(authentication, menu);
    }

}
