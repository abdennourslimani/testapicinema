package fr.semifir.apicinema;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.semifir.apicinema.controllers.CinemaController;
import fr.semifir.apicinema.controllers.FilmController;
import fr.semifir.apicinema.dtos.cinema.CinemaDTO;
import fr.semifir.apicinema.dtos.film.FilmDTO;
import fr.semifir.apicinema.dtos.salle.SalleDTO;
import fr.semifir.apicinema.dtos.seance.SeanceDTO;
import fr.semifir.apicinema.entities.Cinema;
import fr.semifir.apicinema.entities.Film;
import fr.semifir.apicinema.entities.Salle;
import fr.semifir.apicinema.entities.Seance;
import fr.semifir.apicinema.services.CinemaService;
import fr.semifir.apicinema.services.FilmService;
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

import java.util.Date;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = FilmController.class)
public class FilmControllerTest {

    @Autowired
    private MockMvc mockMVC ;


    @MockBean
    private FilmService service ;


    /**
     * find All
     * @throws Exception
     */
    @Test
    public  void testFindFilms() throws  Exception{
        mockMVC.perform(get("/films")) // lancer la requete .
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    /**
     *  Save film
     * @throws Exception
     */
    @Test
    @Disabled
    public void testSaveFilm() throws Exception{

        Gson json = new GsonBuilder().setDateFormat("yyyy-mm-dd").create();


        FilmDTO filmDTO = this.filmDTO();

        String body = json.toJson(filmDTO);
        mockMVC.perform(post("/films")
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
    public  void testFindOneFilmWhereExistantFilm() throws  Exception{
        FilmDTO filmDTO = this.filmDTO();

        BDDMockito.given(service.findByID("1"))
                .willReturn(Optional.of(filmDTO));

        MvcResult result = mockMVC.perform(get("/films/1"))
                .andExpect(status().isOk())
                .andReturn();
        Gson json = new GsonBuilder().setDateFormat("yyyy-mm-dd").create();

        FilmDTO body =   json.fromJson(result.getResponse().getContentAsString(),FilmDTO.class) ;
        System.out.println(body);
        Assertions.assertEquals(body.getNom(),filmDTO.getNom());
    }



    /**
     * find By ID d'ont exist le status n'est pas ok donc test ne passe pas
     * @throws Exception
     */
    @Test
    public  void testFindOneCinemaWhereInexistantCinema() throws  Exception{
        mockMVC.perform(get("/films/1"))
                .andExpect(status().isOk());
    }



    /**
     * update ID  controller
     * @throws Exception
     */
    @Test
    public  void updateEmployee() throws  Exception{

        FilmDTO filmDTO = this.filmDTO();
        FilmDTO filmDTOUpdate = this.filmDTOUpdate();

        BDDMockito.given(service.findByID("1"))
                .willReturn(Optional.of(filmDTO));


        MvcResult result = mockMVC.perform(get("/films/1"))
                .andExpect(status().isOk())
                .andReturn();
        Gson json = new GsonBuilder().setDateFormat("yyyy-mm-dd").create();

        FilmDTO body =   json.fromJson(result.getResponse().getContentAsString(),FilmDTO.class) ;
        BDDMockito.when(service.save(any(FilmDTO.class)))
                .thenReturn(filmDTOUpdate);

        body.setNom("YAMAKURI");

        String bodyToSave = json.toJson(body);
        MvcResult result1 = mockMVC.perform(put("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bodyToSave))
                .andExpect(status().isOk())
                .andReturn();

        FilmDTO bodyFinal =     json.fromJson(result1.getResponse().getContentAsString(),FilmDTO.class);
        Assertions.assertEquals(bodyFinal.getNom(),filmDTOUpdate.getNom());


    }


    SalleDTO salle = new SalleDTO(
            "1",
            3,
            100,
            new Cinema(
            "1",
            "hogar"
    )
    );



            SeanceDTO seance = new SeanceDTO(

            "1",
            new Date(),
                    salle



            );


    private FilmDTO filmDTO(){
        return new FilmDTO(
                "1",
                "abdennour in the forest",
                2f,
                seance

        ) ;
    }






    private FilmDTO filmDTOUpdate(){
        return new FilmDTO(
                "1",
                "hello les chameaux ",
                2f,
                seance

        ) ;

    }



    /**
     *  Delete cinema
     * @throws Exception
     */

    @Test
    public void testDeleteFilm() throws Exception{

        Gson json = new GsonBuilder().setDateFormat("yyyy-mm-dd").create();

        FilmDTO filmDTO = this.filmDTO();

        String body = json.toJson(filmDTO);
        mockMVC.perform(delete("/films")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk());

        //
    }




}
