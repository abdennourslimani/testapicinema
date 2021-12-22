package fr.semifir.apicinema;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
<<<<<<< HEAD
import fr.semifir.apicinema.controllers.CinemaController;
import fr.semifir.apicinema.dtos.cinema.CinemaDTO;
import fr.semifir.apicinema.entities.Cinema;
import fr.semifir.apicinema.services.CinemaService;
=======
import fr.semifir.apicinema.controllers.SalleController;
import fr.semifir.apicinema.controllers.SeanceController;
import fr.semifir.apicinema.dtos.salle.SalleDTO;
import fr.semifir.apicinema.dtos.seance.SeanceDTO;
import fr.semifir.apicinema.entities.Cinema;
import fr.semifir.apicinema.entities.Salle;
import fr.semifir.apicinema.entities.Seance;
import fr.semifir.apicinema.services.SalleService;
import fr.semifir.apicinema.services.SeanceService;
>>>>>>> develop
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

<<<<<<< HEAD
=======
import java.util.Date;
>>>>>>> develop
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

<<<<<<< HEAD
@WebMvcTest(controllers = CinemaController.class)
=======
@WebMvcTest(controllers = SeanceController.class)
>>>>>>> develop
public class SeanceControllerTest {

    @Autowired
    private MockMvc mockMVC ;


    @MockBean
<<<<<<< HEAD
    private CinemaService service ;
=======
    private SeanceService service ;
>>>>>>> develop


    /**
     * find All
     * @throws Exception
     */
    @Test
<<<<<<< HEAD
    public  void testFindCinemas() throws  Exception{
        mockMVC.perform(get("/cinemas")) // lancer la requete .
=======
    public  void testFindSeances() throws  Exception{
        mockMVC.perform(get("/seances")) // lancer la requete .
>>>>>>> develop
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    /**
     *  Save Cinema
     * @throws Exception
     */
    @Test
<<<<<<< HEAD
    @Disabled
    public void testSaveCinema() throws Exception{

        Gson json = new GsonBuilder().create();

        CinemaDTO cinemaDTO = this.cinemaDTO();

        String body = json.toJson(cinemaDTO);
        mockMVC.perform(post("/cinemas")
=======

    public void testSaveSeances() throws Exception{

        Gson json = new GsonBuilder().setDateFormat("yyyy-mm-dd").create();
        SeanceDTO seanceDTO = this.seanceDTO();

        String body = json.toJson(seanceDTO);
        mockMVC.perform(post("/seances")
>>>>>>> develop
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
<<<<<<< HEAD
    public  void testFindOneEmployeesWhereExistantEmployee() throws  Exception{
        CinemaDTO cinemaDTO = this.cinemaDTO();

        BDDMockito.given(service.findByID("1"))
                .willReturn(Optional.of(cinemaDTO));

        MvcResult result = mockMVC.perform(get("/cinemas/1"))
                .andExpect(status().isOk())
                .andReturn();
        Gson json = new GsonBuilder().create();

        CinemaDTO body =   json.fromJson(result.getResponse().getContentAsString(),CinemaDTO.class) ;
        System.out.println(body);
        Assertions.assertEquals(body.getNom(),cinemaDTO.getNom());
=======
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
>>>>>>> develop
    }



    /**
     * find By ID d'ont exist
     * @throws Exception
     */
    @Test
    public  void testFindOneCinemaWhereInexistantCinema() throws  Exception{
<<<<<<< HEAD
        mockMVC.perform(get("/cinemas/1"))
                .andExpect(status().isNotFound());
=======
        mockMVC.perform(get("/seances/1"))
                .andExpect(status().isOk());
>>>>>>> develop
    }



    /**
     * update ID  controller
     * @throws Exception
     */
    @Test
    public  void updateEmployee() throws  Exception{

<<<<<<< HEAD
        CinemaDTO cinemaDTO = this.cinemaDTO();
        CinemaDTO cinemaDTOUpdate = this.cinemaDTOUpdate();

        BDDMockito.given(service.findByID("1"))
                .willReturn(Optional.of(cinemaDTO));


        MvcResult result = mockMVC.perform(get("/cinemas/1"))
=======
        SeanceDTO seanceDTO = this.seanceDTO();
        SeanceDTO seanceDTOUpdate = this.seanceDTOUpdate();

        BDDMockito.given(service.findByID("1"))
                .willReturn( Optional.of(seanceDTO));


        MvcResult result = mockMVC.perform(get("/seances/1"))
>>>>>>> develop
                .andExpect(status().isOk())
                .andReturn();
        Gson json = new GsonBuilder().setDateFormat("yyyy-mm-dd").create();

<<<<<<< HEAD
        CinemaDTO body =   json.fromJson(result.getResponse().getContentAsString(),CinemaDTO.class) ;
        BDDMockito.when(service.save(any(Cinema.class)))
                .thenReturn(cinemaDTOUpdate);

        body.setNom("abdelhak");

        String bodyToSave = json.toJson(body);
        MvcResult result1 = mockMVC.perform(put("/cinemas")
=======
        SeanceDTO body =   json.fromJson(result.getResponse().getContentAsString(),SeanceDTO.class) ;
        BDDMockito.when(service.save(any(Seance.class)))
                .thenReturn(seanceDTOUpdate);

        body.setSalle(salleupdate);

        String bodyToSave = json.toJson(body);
        MvcResult result1 = mockMVC.perform(put("/seances")
>>>>>>> develop
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bodyToSave))
                .andExpect(status().isOk())
                .andReturn();

<<<<<<< HEAD
        CinemaDTO bodyFinal =     json.fromJson(result1.getResponse().getContentAsString(),CinemaDTO.class);
        Assertions.assertEquals(bodyFinal.getNom(),cinemaDTOUpdate.getNom());
=======
        SeanceDTO bodyFinal =     json.fromJson(result1.getResponse().getContentAsString(),SeanceDTO.class);
        Assertions.assertEquals(bodyFinal.getSalle().getNbrPlace(),seanceDTOUpdate.getSalle().getNbrPlace());
>>>>>>> develop


    }

<<<<<<< HEAD


    private CinemaDTO cinemaDTO(){
        return new CinemaDTO(
                "1",
                "abdennour"
        ) ;
    }


    private Cinema cinema(){
        return new Cinema(
                "1",
                "abdennour"
        ) ;
=======
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
>>>>>>> develop
    }



<<<<<<< HEAD
    private CinemaDTO cinemaDTOUpdate(){
        return new CinemaDTO(
                "1",
                "abdelhak"

        ) ;
=======
    private SeanceDTO  seanceDTOUpdate(){
        return new SeanceDTO(

                "1",
                new Date(),
                salle
        );
>>>>>>> develop

    }



    /**
     *  Delete cinema
     * @throws Exception
     */

    @Test
<<<<<<< HEAD
    public void testDeleteEmployee() throws Exception{

        Gson json = new GsonBuilder().setDateFormat("yyyy-mm-dd").create();

        CinemaDTO cinemaDTO = this.cinemaDTO();

        String body = json.toJson(cinemaDTO);
        mockMVC.perform(delete("/cinemas")
=======
    public void testDeleteSeance() throws Exception{

        Gson json = new GsonBuilder().setDateFormat("yyyy-mm-dd").create();

        SeanceDTO seanceDTO = this.seanceDTO();

        String body = json.toJson(seanceDTO);
        mockMVC.perform(delete("/seances")
>>>>>>> develop
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk());

        //
    }




}
