package fr.semifir.apicinema;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.semifir.apicinema.controllers.CinemaController;
import fr.semifir.apicinema.dtos.cinema.CinemaDTO;
import fr.semifir.apicinema.entities.Cinema;
import fr.semifir.apicinema.services.CinemaService;
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

@WebMvcTest(controllers = CinemaController.class)
public class FilmControllerTest {

    @Autowired
    private MockMvc mockMVC ;


    @MockBean
    private CinemaService service ;


    /**
     * find All
     * @throws Exception
     */
    @Test
    public  void testFindCinemas() throws  Exception{
        mockMVC.perform(get("/cinemas")) // lancer la requete .
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    /**
     *  Save Cinema
     * @throws Exception
     */
    @Test
    @Disabled
    public void testSaveCinema() throws Exception{

        Gson json = new GsonBuilder().create();

        CinemaDTO cinemaDTO = this.cinemaDTO();

        String body = json.toJson(cinemaDTO);
        mockMVC.perform(post("/cinemas")
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
    }



    /**
     * find By ID d'ont exist
     * @throws Exception
     */
    @Test
    public  void testFindOneCinemaWhereInexistantCinema() throws  Exception{
        mockMVC.perform(get("/cinemas/1"))
                .andExpect(status().isNotFound());
    }



    /**
     * update ID  controller
     * @throws Exception
     */
    @Test
    public  void updateEmployee() throws  Exception{

        CinemaDTO cinemaDTO = this.cinemaDTO();
        CinemaDTO cinemaDTOUpdate = this.cinemaDTOUpdate();

        BDDMockito.given(service.findByID("1"))
                .willReturn(Optional.of(cinemaDTO));


        MvcResult result = mockMVC.perform(get("/cinemas/1"))
                .andExpect(status().isOk())
                .andReturn();
        Gson json = new GsonBuilder().setDateFormat("yyyy-mm-dd").create();

        CinemaDTO body =   json.fromJson(result.getResponse().getContentAsString(),CinemaDTO.class) ;
        BDDMockito.when(service.save(any(Cinema.class)))
                .thenReturn(cinemaDTOUpdate);

        body.setNom("abdelhak");

        String bodyToSave = json.toJson(body);
        MvcResult result1 = mockMVC.perform(put("/cinemas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bodyToSave))
                .andExpect(status().isOk())
                .andReturn();

        CinemaDTO bodyFinal =     json.fromJson(result1.getResponse().getContentAsString(),CinemaDTO.class);
        Assertions.assertEquals(bodyFinal.getNom(),cinemaDTOUpdate.getNom());


    }



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
    }



    private CinemaDTO cinemaDTOUpdate(){
        return new CinemaDTO(
                "1",
                "abdelhak"

        ) ;

    }



    /**
     *  Delete cinema
     * @throws Exception
     */

    @Test
    public void testDeleteEmployee() throws Exception{

        Gson json = new GsonBuilder().setDateFormat("yyyy-mm-dd").create();

        CinemaDTO cinemaDTO = this.cinemaDTO();

        String body = json.toJson(cinemaDTO);
        mockMVC.perform(delete("/cinemas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk());

        //
    }




}
