package fr.semifir.apicinema;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import fr.semifir.apicinema.controllers.SeanceController;
import fr.semifir.apicinema.dtos.salle.SalleDTO;
import fr.semifir.apicinema.dtos.seance.SeanceDTO;
import fr.semifir.apicinema.entities.Cinema;
import fr.semifir.apicinema.entities.Salle;
import fr.semifir.apicinema.entities.Seance;

import fr.semifir.apicinema.services.SeanceService;
import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Date;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = SeanceController.class)
public class SeanceControllerTest {

    @Autowired
    private MockMvc mockMVC ;


    @MockBean
    private SeanceService service ;


    /**
     * find All
     * @throws Exception
     */
    @Test
    public  void testFindSeances() throws  Exception{
        mockMVC.perform(get("/seances")) // lancer la requete .
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    /**
     *  Save Cinema
     * @throws Exception
     */
    @Test

    public void testSaveSeances() throws Exception{

        Gson json = new GsonBuilder().setDateFormat("yyyy-mm-dd").create();
        SeanceDTO seanceDTO = this.seanceDTO();

        String body = json.toJson(seanceDTO);
        mockMVC.perform(post("/seances")
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
    public  void testFindOneSeanceWhereExistantSeance() throws  Exception{
        SeanceDTO seanceDTO = this.seanceDTO();

        BDDMockito.given(service.findByID("1"))
                .willReturn(Optional.of(seanceDTO));

        MvcResult result = mockMVC.perform(get("/seances/1"))
                .andExpect(status().isOk())
                .andReturn();
        Gson json = new GsonBuilder().setDateFormat("yyyy-mm-dd").create();


        SeanceDTO body =   json.fromJson(result.getResponse().getContentAsString(),SeanceDTO.class) ;
        System.out.println(body);
        Assertions.assertEquals(body.getSalle().getNumDeSalle(),seanceDTO.getSalle().getNumDeSalle());
    }



    /**
     * find By ID d'ont exist
     * @throws Exception
     */
    @Test
    public  void testFindOneSeanceWhereInexistantSeance() throws  Exception{
        mockMVC.perform(get("/seances/1"))
                .andExpect(status().isOk());
    }



    /**
     * update ID  controller
     * @throws Exception
     */
    @Test
    public  void updateSeance() throws  Exception{

        SeanceDTO seanceDTO = this.seanceDTO();
        SeanceDTO seanceDTOUpdate = this.seanceDTOUpdate();

        BDDMockito.given(service.findByID("1"))
                .willReturn( Optional.of(seanceDTO));


        MvcResult result = mockMVC.perform(get("/seances/1"))
                .andExpect(status().isOk())
                .andReturn();
        Gson json = new GsonBuilder().setDateFormat("yyyy-mm-dd").create();

        SeanceDTO body =   json.fromJson(result.getResponse().getContentAsString(),SeanceDTO.class) ;
        BDDMockito.when(service.save(any(Seance.class)))
                .thenReturn(seanceDTOUpdate);

        body.setSalle(salleupdate);

        String bodyToSave = json.toJson(body);
        MvcResult result1 = mockMVC.perform(put("/seances")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bodyToSave))
                .andExpect(status().isOk())
                .andReturn();

        SeanceDTO bodyFinal =     json.fromJson(result1.getResponse().getContentAsString(),SeanceDTO.class);
        Assertions.assertEquals(bodyFinal.getSalle().getNbrPlace(),seanceDTOUpdate.getSalle().getNbrPlace());


    }

  SalleDTO salle =   new SalleDTO(
                "1",
                        3,
                        100000,
                        new Cinema(
                        "1",
                        "hogar"
    )
        );



    SalleDTO salleupdate =   new SalleDTO(
            "1",
            3,
            100000,
            new Cinema(
                    "1",
                    "hogar"
            )
    );


    Salle salle1 =   new Salle(
            "1",
            5,
            500,
            new Cinema(
                    "1",
                    "hogar"
            )
    );


    private SeanceDTO seanceDTO(){
        return new SeanceDTO(

                "1",
                new Date(),
                salleupdate
        );

    }




    private Seance seance(){
        return new Seance(

                "1",
                new Date(),
                salle1

        );
    }



    private SeanceDTO  seanceDTOUpdate(){
        return new SeanceDTO(

                "1",
                new Date(),
                salle
        );

    }



    /**
     *  Delete cinema
     * @throws Exception
     */

    @Test
    public void testDeleteSeance() throws Exception{

        Gson json = new GsonBuilder().setDateFormat("yyyy-mm-dd").create();

        SeanceDTO seanceDTO = this.seanceDTO();

        String body = json.toJson(seanceDTO);
        mockMVC.perform(delete("/seances")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk());

        //
    }




}
