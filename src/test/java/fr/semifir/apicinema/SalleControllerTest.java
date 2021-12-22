package fr.semifir.apicinema;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.semifir.apicinema.controllers.CinemaController;
import fr.semifir.apicinema.controllers.SalleController;
import fr.semifir.apicinema.dtos.cinema.CinemaDTO;
import fr.semifir.apicinema.dtos.salle.SalleDTO;
import fr.semifir.apicinema.entities.Cinema;
import fr.semifir.apicinema.entities.Salle;
import fr.semifir.apicinema.services.CinemaService;
import fr.semifir.apicinema.services.SalleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = SalleController.class)
public class SalleControllerTest {

    @Autowired
    private MockMvc mockMVC ;


    @MockBean
    private SalleService service ;


    /**
     * find All
     * @throws Exception
     */
    @Test
    public  void testFindSalles() throws  Exception{
        mockMVC.perform(get("/salles")) // lancer la requete .
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    /**
     *  Save Cinema
     * @throws Exception
     */
    @Test
    @Disabled
    public void testSaveSalles() throws Exception{

        Gson json = new GsonBuilder().create();

        SalleDTO salleDTO = this.salleDTO();

        String body = json.toJson(salleDTO);
        mockMVC.perform(post("/salles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk());

        //
    }



    /**
     *  find buu id cinema
     * @throws Exception
     */
    @Test
    public  void testFindOneEmployeesWhereExistantEmployee() throws  Exception{
        SalleDTO salleDTO = this.salleDTO();

        BDDMockito.given(service.findByID("1"))
                .willReturn(Optional.of(salleDTO));

        MvcResult result = mockMVC.perform(get("/salles/1"))
                .andExpect(status().isOk())
                .andReturn();
        Gson json = new GsonBuilder().create();

        SalleDTO body =   json.fromJson(result.getResponse().getContentAsString(),SalleDTO.class) ;
        System.out.println(body);
        Assertions.assertEquals(body.getNumDeSalle(),salleDTO.getNumDeSalle());
    }



    /**
     * find By ID d'ont exist
     * @throws Exception
     */
    @Test
    public  void testFindOneCinemaWhereInexistantCinema() throws  Exception{
        mockMVC.perform(get("/salles/1"))
                .andExpect(status().isOk());
    }



    /**
     * update ID  controller
     * @throws Exception
     */
    @Test
    public  void updateEmployee() throws  Exception{

        SalleDTO salleDTO = this.salleDTO();
        SalleDTO salleDTOUpdate = this.salleDTOUpdate();

        BDDMockito.given(service.findByID("1"))
                .willReturn(Optional.of(salleDTO));


        MvcResult result = mockMVC.perform(get("/salles/1"))
                .andExpect(status().isOk())
                .andReturn();
        Gson json = new GsonBuilder().setDateFormat("yyyy-mm-dd").create();

        SalleDTO body =   json.fromJson(result.getResponse().getContentAsString(),SalleDTO.class) ;
        BDDMockito.when(service.save(any(Salle.class)))
                .thenReturn(salleDTOUpdate);

        body.setNumDeSalle(30);

        String bodyToSave = json.toJson(body);
        MvcResult result1 = mockMVC.perform(put("/salles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bodyToSave))
                .andExpect(status().isOk())
                .andReturn();

        SalleDTO bodyFinal =     json.fromJson(result1.getResponse().getContentAsString(),SalleDTO.class);
        Assertions.assertEquals(bodyFinal.getNumDeSalle(),salleDTOUpdate.getNumDeSalle());


    }



    private SalleDTO salleDTO(){
        return new SalleDTO(
                "1",
                3,
                100,
                new Cinema(
                        "1",
                        "hogar"
                )
        );
    }


    private Salle salle(){
        return new Salle(
                "1",
                30,
                10000,
                new Cinema(
                        "1",
                        "tahiti"
                )
        );
    }



    private SalleDTO  salleDTOUpdate(){
        return new SalleDTO(
                "1",
                10,
                50000,
                new Cinema(
                        "1",
                        "hogar"
                ));

    }



    /**
     *  Delete cinema
     * @throws Exception
     */

    @Test
    public void testDeleteSalle() throws Exception{

        Gson json = new GsonBuilder().setDateFormat("yyyy-mm-dd").create();

        SalleDTO salleDTO = this.salleDTO();

        String body = json.toJson(salleDTO);
        mockMVC.perform(delete("/salles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk());

        //
    }




}
