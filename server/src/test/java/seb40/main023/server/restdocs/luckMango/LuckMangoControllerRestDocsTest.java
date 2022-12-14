//package seb40.main023.server.restdocs.luckMango;
//
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Sort;
//import org.springframework.http.MediaType;
//import org.springframework.restdocs.payload.JsonFieldType;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.ResultActions;
//import seb40.main023.server.luckMango.controller.LuckMangoController;
//import seb40.main023.server.luckMango.dto.LuckMangoPatchDto;
//import seb40.main023.server.luckMango.dto.LuckMangoPostDto;
//import seb40.main023.server.luckMango.dto.LuckMangoResponseDto;
//import seb40.main023.server.luckMango.entity.LuckMango;
//import seb40.main023.server.luckMango.mapper.LuckMangoMapper;
//import seb40.main023.server.luckMango.service.LuckMangoService;
//import com.google.gson.Gson;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
//import org.springframework.test.web.servlet.MockMvc;
//import seb40.main023.server.member.dto.MemberResponseDto_Mango;
//
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.mockito.BDDMockito.given;
//import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
//import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
//import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
//import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
//import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
//import static org.springframework.restdocs.payload.PayloadDocumentation.*;
//import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
//import static org.springframework.restdocs.request.RequestDocumentation.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import static seb40.main023.server.restdocs.util.ApiDocumentUtils.getRequestPreProcessor;
//import static seb40.main023.server.restdocs.util.ApiDocumentUtils.getResponsePreProcessor;
//
//@WebMvcTest(LuckMangoController.class)
//@MockBean(JpaMetamodelMappingContext.class)
//@AutoConfigureRestDocs
//public class LuckMangoControllerRestDocsTest {
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private LuckMangoService luckMangoService;
//
//    @MockBean
//    private LuckMangoMapper luckMangoMapper;
//
//    @Autowired
//    private Gson gson;
//
//
//    @Test
//    public void postLuckMangoTest() throws Exception {
//        // given
//        LuckMangoPostDto post = LuckMangoPostDto.builder()
//                .memberId(1L)
//                .bgImage("bg.jpg")
//                .bgVideo("bgVideo.mp4")
//                .mangoBody("??????")
//                .title("??????")
//                .reveal(true)
//                .build();
//
//
//        LuckMangoResponseDto luckMangoResponseDto =
//                LuckMangoResponseDto.builder()
//                        .luckMangoId(1L)
//                        .bgImage("bg.jpg")
//                        .bgVideo("bgVideo.mp4")
//                        .title("??????")
//                        .mangoBody("??????")
//                        .likeCount(0)
//                        .reveal(true)
//                        .member(new MemberResponseDto_Mango(1L,"???????????????","test@gmail.com","http://aa.aa.com"))
//                        .createdAt(LocalDateTime.of(2022, 10, 31, 10, 0, 0))
//                        .modifiedAt(LocalDateTime.of(2022, 10, 31, 10, 0, 0))
//                        .build();
//
//        String luckMango = gson.toJson(post);
//
//        given(luckMangoMapper.luckMangoPostDtoToluckMango(Mockito.any(LuckMangoPostDto.class))).willReturn(Mockito.mock(LuckMango.class));
//        given(luckMangoService.createLuckMango(Mockito.any(LuckMango.class))).willReturn(Mockito.mock(LuckMango.class));
//        given(luckMangoMapper.luckMangoToLuckMangoResponseDto(Mockito.any(LuckMango.class))).willReturn(luckMangoResponseDto);
//
//        // when
//        ResultActions actions =
//                mockMvc.perform(
//                        post("/luckMango")
//                                .accept(MediaType.APPLICATION_JSON)
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .content(luckMango));
//
//        // then
//        actions
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.data.title").value(post.getTitle()))
//                .andExpect(jsonPath("$.data.bgImage").value(post.getBgImage()))
//                .andExpect(jsonPath("$.data.bgVideo").value(post.getBgVideo()))
//                .andExpect(jsonPath("$.data.mangoBody").value(post.getMangoBody()))
//                .andExpect(jsonPath("$.data.reveal").value(true))
//                .andDo(document("post-luckMango",
//                        getRequestPreProcessor(),
//                        getResponsePreProcessor(),
//                        requestFields(
//                                List.of(
//                                        fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("????????????"),
//                                        fieldWithPath("title").type(JsonFieldType.STRING).description("??????"),
//                                        fieldWithPath("mangoBody").type(JsonFieldType.STRING).description("??????"),
//                                        fieldWithPath("bgImage").type(JsonFieldType.STRING).description("???????????????"),
//                                        fieldWithPath("bgVideo").type(JsonFieldType.STRING).description("?????????"),
//                                        fieldWithPath("reveal").type(JsonFieldType.BOOLEAN).description("true")
//                                )
//                        ),
//                        responseFields(
//                                List.of(
//                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("?????? ?????????"),
//                                        fieldWithPath("data.luckMangoId").type(JsonFieldType.NUMBER).description("????????? ?????????"),
//                                        fieldWithPath("data.title").type(JsonFieldType.STRING).description("??????"),
//                                        fieldWithPath("data.mangoBody").type(JsonFieldType.STRING).description("??????"),
//                                        fieldWithPath("data.bgImage").type(JsonFieldType.STRING).description("???????????????"),
//                                        fieldWithPath("data.bgVideo").type(JsonFieldType.STRING).description("?????????"),
//                                        fieldWithPath("data.likeCount").type(JsonFieldType.NUMBER).description("????????????"),
//                                        fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("????????????"),
//                                        fieldWithPath("data.modifiedAt").type(JsonFieldType.STRING).description("????????????"),
//                                        fieldWithPath("data.reveal").type(JsonFieldType.STRING).description("???????????? ??? :'true'??? ????????? ?????????"),
//                                        fieldWithPath("data.member").type(JsonFieldType.OBJECT).description("?????? ?????????"),
//                                        fieldWithPath("data.member.memberId").type(JsonFieldType.NUMBER).description("?????? ??????"),
//                                        fieldWithPath("data.member.name").type(JsonFieldType.STRING).description("?????? ?????????"),
//                                        fieldWithPath("data.member.email").type(JsonFieldType.STRING).description("?????? ?????????"),
//                                        fieldWithPath("data.member.imgUrl").type(JsonFieldType.STRING).description("?????? ?????????")
//                                )
//                        )
//                ));
//
//    }
//
//    @Test
//    public void patchLuckMangoTest() throws Exception{
//        // given
//        long luckMango_Id = 1L;
//        LuckMangoPatchDto patch = LuckMangoPatchDto.builder()
//                .luckMangoId(luckMango_Id)
//                .bgImage("bg.jpg")
//                .bgVideo("bgVideo.mp4")
//                .title("??????")
//                .mangoBody("????????? ??????")
//                .likeCount(0)
//                .reveal("???????????? ??? :'true'??? ????????? ????????? ")
//                .build();
//
//        LuckMangoResponseDto luckMangoResponseDto =
//                LuckMangoResponseDto.builder()
//                        .luckMangoId(luckMango_Id)
//                        .bgImage("bg.jpg")
//                        .bgVideo("bgVideo.mp4")
//                        .title("??????")
//                        .mangoBody("????????? ??????")
//                        .likeCount(0)
//                        .reveal("???????????? ??? :'true'??? ????????? ????????? ")
//                        .member(new MemberResponseDto_Mango(1L,"???????????????","test@gmail.com","http://aa.aa.com"))
//                        .createdAt(LocalDateTime.of(2022, 10, 31, 10, 0, 0))
//                        .modifiedAt(LocalDateTime.of(2022, 10, 31, 10, 0, 0))
//                        .build();
//
//        String luckMango = gson.toJson(patch);
//
//        given(luckMangoMapper.luckMangoPatchDtoToluckMango(Mockito.any(LuckMangoPatchDto.class))).willReturn(Mockito.mock(LuckMango.class));
//        given(luckMangoService.updateLuckMango(Mockito.any(LuckMango.class))).willReturn(Mockito.mock(LuckMango.class));
//        given(luckMangoMapper.luckMangoToLuckMangoResponseDto(Mockito.any(LuckMango.class))).willReturn(luckMangoResponseDto);
//
//        // when
//        ResultActions actions =
//                mockMvc.perform(
//                        patch("/luckMango/{luckMango-id}",1,luckMango_Id)
//                                .accept(MediaType.APPLICATION_JSON)
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .content(luckMango));
//
//        // then
//        actions
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.data.luckMangoId").value(patch.getLuckMangoId()))
//                .andExpect(jsonPath("$.data.title").value(patch.getTitle()))
//                .andExpect(jsonPath("$.data.bgImage").value(patch.getBgImage()))
//                .andExpect(jsonPath("$.data.bgVideo").value(patch.getBgVideo()))
//                .andExpect(jsonPath("$.data.likeCount").value(patch.getLikeCount()))
//                .andExpect(jsonPath("$.data.mangoBody").value(patch.getMangoBody()))
//                .andExpect(jsonPath("$.data.reveal").value(patch.getReveal()))
//                .andDo(document("patch-luckMango",
//                        getRequestPreProcessor(),
//                        getResponsePreProcessor(),
//                        requestFields(
//                                List.of(
//                                        fieldWithPath("luckMangoId").type(JsonFieldType.NUMBER).description("???????????????"),
//                                        fieldWithPath("title").type(JsonFieldType.STRING).description("??????"),
//                                        fieldWithPath("mangoBody").type(JsonFieldType.STRING).description("????????? ??????"),
//                                        fieldWithPath("bgImage").type(JsonFieldType.STRING).description("???????????????"),
//                                        fieldWithPath("bgVideo").type(JsonFieldType.STRING).description("?????????"),
//                                        fieldWithPath("likeCount").type(JsonFieldType.NUMBER).description("????????????"),
//                                        fieldWithPath("reveal").type(JsonFieldType.STRING).description("???????????? ??? :'true'??? ????????? ?????????")
//                                )
//                        ),
//                        responseFields(
//                                List.of(
//                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("?????? ?????????"),
//                                        fieldWithPath("data.luckMangoId").type(JsonFieldType.NUMBER).description("????????? ?????????"),
//                                        fieldWithPath("data.title").type(JsonFieldType.STRING).description("??????"),
//                                        fieldWithPath("data.mangoBody").type(JsonFieldType.STRING).description("??????"),
//                                        fieldWithPath("data.bgImage").type(JsonFieldType.STRING).description("???????????????"),
//                                        fieldWithPath("data.bgVideo").type(JsonFieldType.STRING).description("?????????"),
//                                        fieldWithPath("data.likeCount").type(JsonFieldType.NUMBER).description("????????????"),
//                                        fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("????????????"),
//                                        fieldWithPath("data.modifiedAt").type(JsonFieldType.STRING).description("????????????"),
//                                        fieldWithPath("data.reveal").type(JsonFieldType.STRING).description("???????????? ??? :'true'??? ????????? ?????????"),
//                                        fieldWithPath("data.member").type(JsonFieldType.OBJECT).description("?????? ?????????"),
//                                        fieldWithPath("data.member.memberId").type(JsonFieldType.NUMBER).description("?????? ??????"),
//                                        fieldWithPath("data.member.name").type(JsonFieldType.STRING).description("?????? ?????????"),
//                                        fieldWithPath("data.member.email").type(JsonFieldType.STRING).description("?????? ?????????"),
//                                        fieldWithPath("data.member.imgUrl").type(JsonFieldType.STRING).description("?????? ?????????")
//                                )
//                        )
//                ));
//
//    }
//
//    @Test
//    public void getLuckMangoTest() throws Exception {
//        // given
//        long luckMangoId = 1L;
//        LuckMangoResponseDto responseDto =
//                LuckMangoResponseDto.builder()
//                        .luckMangoId(luckMangoId)
//                        .bgImage("bg.jpg")
//                        .bgVideo("bgVideo.mp4")
//                        .title("??????")
//                        .mangoBody("????????? ??????")
//                        .likeCount(0)
//                        .reveal("???????????? ??? :'true'??? ????????? ????????? ")
//                        .member(new MemberResponseDto_Mango(1L,"???????????????","test@gmail.com","http://aa.aa.com"))
//                        .createdAt(LocalDateTime.of(2022, 10, 31, 10, 0, 0))
//                        .modifiedAt(LocalDateTime.of(2022, 10, 31, 10, 0, 0))
//                        .build();
//
//        String content = gson.toJson(responseDto);
//        given(luckMangoService.findLuckMango(luckMangoId)).willReturn(Mockito.mock(LuckMango.class));
//        given(luckMangoMapper.luckMangoToLuckMangoResponseDto(Mockito.any(LuckMango.class))).willReturn(responseDto);
//        // when
//        ResultActions actions =
//                mockMvc.perform(
//                        get("/luckMango/{luckMango-id}", 1L)
//                        .accept(MediaType.APPLICATION_JSON)
//                        .contentType(MediaType.APPLICATION_JSON)
////                        .content(content)
//                );
//        // then
//        actions.andExpect(status().isOk())
//                .andDo(document("get-luckMango",
//                        getRequestPreProcessor(),
//                        getResponsePreProcessor(),
////                        requestFields(
////                                List.of(
////                                        fieldWithPath("luckMangoId").type(JsonFieldType.NUMBER).description("???????????????"),
////                                        fieldWithPath("memberId").type(JsonFieldType.NUMBER).description("????????????"),
////                                        fieldWithPath("title").type(JsonFieldType.STRING).description("??????"),
////                                        fieldWithPath("bgImage").type(JsonFieldType.STRING).description("???????????????"),
////                                        fieldWithPath("bgVideo").type(JsonFieldType.STRING).description("?????????"),
////                                        fieldWithPath("likeCount").type(JsonFieldType.NUMBER).description("????????????"),
////                                        fieldWithPath("createdAt").type(JsonFieldType.STRING).description("????????????"),
////                                        fieldWithPath("modifiedAt").type(JsonFieldType.STRING).description("????????????")
////                                )
////                        ),
//                        responseFields(
//                                List.of(
//                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("?????? ?????????"),
//                                        fieldWithPath("data.luckMangoId").type(JsonFieldType.NUMBER).description("????????? ?????????"),
//                                        fieldWithPath("data.title").type(JsonFieldType.STRING).description("??????"),
//                                        fieldWithPath("data.mangoBody").type(JsonFieldType.STRING).description("??????"),
//                                        fieldWithPath("data.bgImage").type(JsonFieldType.STRING).description("???????????????"),
//                                        fieldWithPath("data.bgVideo").type(JsonFieldType.STRING).description("?????????"),
//                                        fieldWithPath("data.likeCount").type(JsonFieldType.NUMBER).description("????????????"),
//                                        fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("????????????"),
//                                        fieldWithPath("data.modifiedAt").type(JsonFieldType.STRING).description("????????????"),
//                                        fieldWithPath("data.reveal").type(JsonFieldType.STRING).description("???????????? ??? :'true'??? ????????? ?????????"),
//                                        fieldWithPath("data.member").type(JsonFieldType.OBJECT).description("?????? ?????????"),
//                                        fieldWithPath("data.member.memberId").type(JsonFieldType.NUMBER).description("?????? ??????"),
//                                        fieldWithPath("data.member.name").type(JsonFieldType.STRING).description("?????? ?????????"),
//                                        fieldWithPath("data.member.email").type(JsonFieldType.STRING).description("?????? ?????????"),
//                                        fieldWithPath("data.member.imgUrl").type(JsonFieldType.STRING).description("?????? ?????????")
//                                )
//                        )
//                ));
//
//    }
//
//    @Test
//    public void getLuckMangosTest() throws Exception {
//        // given
//        int page = 1;
//        int size = 10;
//
//        LuckMango luckMango1 = new LuckMango();
//        luckMango1.setLuckMangoId(1L);
//        luckMango1.setMember(null);
//        luckMango1.setBgVideo("?????????");
//        luckMango1.setBgImage("????????????");
//        luckMango1.setTitle("????????? ?????????");
//
//        LuckMango luckMango2 = new LuckMango();
//        luckMango2.setLuckMangoId(2L);
//        luckMango2.setMember(null);
//        luckMango2.setBgVideo("?????????");
//        luckMango2.setBgImage("????????????");
//        luckMango2.setTitle("????????? ?????????");
//
//        List<LuckMango> luckMangos = new ArrayList<>();
//        luckMangos.add(luckMango1);
//        luckMangos.add(luckMango2);
//
//        Page<LuckMango> pageLuckMango = new PageImpl<>(luckMangos);
//
//        LuckMangoResponseDto responseDto1 = LuckMangoResponseDto.builder()
//                .luckMangoId(1L)
//                .bgImage("bg.jpg")
//                .bgVideo("bgVideo.mp4")
//                .title("?????? 1?????? 1??? ?????????")
//                .mangoBody("??????")
//                .likeCount(0)
//                .reveal("true")
//                .member(new MemberResponseDto_Mango(1L,"???????????????","test@gmail.com","http://aa.aa.com"))
//                .createdAt(LocalDateTime.of(2022, 10, 31, 10, 0, 0))
//                .modifiedAt(LocalDateTime.of(2022, 10, 31, 10, 0, 0))
//                .build();
//
//        LuckMangoResponseDto responseDto2 = LuckMangoResponseDto.builder()
//                .luckMangoId(2L)
//                .bgImage("bg.jpg")
//                .bgVideo("bgVideo.mp4")
//                .title("?????? 2?????? 1??? ?????????")
//                .mangoBody("??????")
//                .likeCount(0)
//                .reveal("true")
//                .member(new MemberResponseDto_Mango(2L,"???????????????","test2@gmail.com","http://aa.aa.com"))
//                .createdAt(LocalDateTime.of(2022, 10, 31, 10, 0, 0))
//                .modifiedAt(LocalDateTime.of(2022, 10, 31, 10, 0, 0))
//                .build();
//
//        List<LuckMangoResponseDto> luckMangoResponseDtos = List.of(responseDto1, responseDto2);
//
//        given(luckMangoService.findLuckMangos(Mockito.anyInt(),Mockito.anyInt())).willReturn(pageLuckMango);
//        given(luckMangoMapper.luckMangoToLuckMangoResponseDtos(Mockito.any(List.class))).willReturn(luckMangoResponseDtos);
//        // when
//        ResultActions actions =
//                mockMvc.perform(
//                        get("/luckMango?page={page}&size={size}",1,10,page,size)
//                                .accept(MediaType.APPLICATION_JSON)
//                                .contentType(MediaType.APPLICATION_JSON)
////                                .content(content)
//                );
//
//        // then
//        actions
//                .andExpect(status().isOk())
//                .andDo(document("get-MeberLuckMango",
//                        getRequestPreProcessor(),
//                        getResponsePreProcessor(),
//                        requestParameters(
//                                parameterWithName("page").description("?????? ?????????"),
//                                parameterWithName("size").description("???????????? ??????")
//                        ),
//                        responseFields(
//                                List.of(
//                                        fieldWithPath("data").type(JsonFieldType.ARRAY).description("?????? ?????????"),
//                                        fieldWithPath("data[].luckMangoId").type(JsonFieldType.NUMBER).description("????????? ?????????"),
//                                        fieldWithPath("data[].title").type(JsonFieldType.STRING).description("??????"),
//                                        fieldWithPath("data[].mangoBody").type(JsonFieldType.STRING).description("??????"),
//                                        fieldWithPath("data[].bgImage").type(JsonFieldType.STRING).description("???????????????"),
//                                        fieldWithPath("data[].bgVideo").type(JsonFieldType.STRING).description("?????????"),
//                                        fieldWithPath("data[].likeCount").type(JsonFieldType.NUMBER).description("????????????"),
//                                        fieldWithPath("data[].createdAt").type(JsonFieldType.STRING).description("????????????"),
//                                        fieldWithPath("data[].modifiedAt").type(JsonFieldType.STRING).description("????????????"),
//                                        fieldWithPath("data[].reveal").type(JsonFieldType.STRING).description("???????????? ??? :'true'??? ????????? ?????????"),
//                                        fieldWithPath("data[].member").type(JsonFieldType.OBJECT).description("?????? ?????????"),
//                                        fieldWithPath("data[].member.memberId").type(JsonFieldType.NUMBER).description("?????? ??????"),
//                                        fieldWithPath("data[].member.name").type(JsonFieldType.STRING).description("?????? ?????????"),
//                                        fieldWithPath("data[].member.email").type(JsonFieldType.STRING).description("?????? ?????????"),
//                                        fieldWithPath("data[].member.imgUrl").type(JsonFieldType.STRING).description("?????? ?????????"),
//                                        fieldWithPath("pageInfo.page").type(JsonFieldType.NUMBER).description("?????? ?????????"),
//                                        fieldWithPath("pageInfo.size").type(JsonFieldType.NUMBER).description("???????????? ??????"),
//                                        fieldWithPath("pageInfo.totalElements").type(JsonFieldType.NUMBER).description("????????? ??? ??????"),
//                                        fieldWithPath("pageInfo.totalPages").type(JsonFieldType.NUMBER).description("??? ????????????")
//                                )
//                        )
//                ));
//    }
//
//    @Test
//    public void getMemberLuckMangoTest() throws Exception {
//        // given
//        int page = 1;
//        int size = 10;
//        long memberId = 1L;
//        String sort = "desc";
//
//        LuckMango luckMango1 = new LuckMango();
//        luckMango1.setLuckMangoId(1L);
//        luckMango1.setMember(null);
//        luckMango1.setBgVideo("?????????");
//        luckMango1.setBgImage("????????????");
//        luckMango1.setTitle("????????? ?????????");
//
//        LuckMango luckMango2 = new LuckMango();
//        luckMango2.setLuckMangoId(2L);
//        luckMango2.setMember(null);
//        luckMango2.setBgVideo("?????????");
//        luckMango2.setBgImage("????????????");
//        luckMango2.setTitle("????????? ?????????");
//
//        List<LuckMango> luckMangos = new ArrayList<>();
//        luckMangos.add(luckMango1);
//        luckMangos.add(luckMango2);
//
//        PageRequest pageRequest = PageRequest.of(page,size,Sort.by(sort).descending());
//        Page<LuckMango> pageLuckMango = new PageImpl<>(luckMangos);
//
//        LuckMangoResponseDto responseDto1 = LuckMangoResponseDto.builder()
//                .luckMangoId(1L)
//                .bgImage("bg.jpg")
//                .bgVideo("bgVideo.mp4")
//                .title("?????? 1?????? 1??? ?????????")
//                .mangoBody("??????")
//                .likeCount(0)
//                .reveal("true")
//                .member(new MemberResponseDto_Mango(1L,"???????????????","test@gmail.com","http://aa.aa.com"))
//                .createdAt(LocalDateTime.of(2022, 10, 31, 10, 0, 0))
//                .modifiedAt(LocalDateTime.of(2022, 10, 31, 10, 0, 0))
//                .build();
//
//        LuckMangoResponseDto responseDto2 = LuckMangoResponseDto.builder()
//                .luckMangoId(1L)
//                .bgImage("bg.jpg")
//                .bgVideo("bgVideo.mp4")
//                .title("?????? 1?????? 2??? ?????????")
//                .mangoBody("??????")
//                .likeCount(0)
//                .reveal("null")
//                .member(new MemberResponseDto_Mango(1L,"???????????????","test@gmail.com","http://aa.aa.com"))
//                .createdAt(LocalDateTime.of(2022, 10, 31, 10, 0, 0))
//                .modifiedAt(LocalDateTime.of(2022, 10, 31, 10, 0, 0))
//                .build();
//
//
//
//        List<LuckMangoResponseDto> luckMangoResponseDtos = List.of(responseDto1, responseDto2);
//
//        given(luckMangoService.searchLuckMango(Mockito.anyLong(),Mockito.anyInt(),Mockito.anyInt(),Mockito.anyString())).willReturn(pageLuckMango);
//        given(luckMangoMapper.luckMangoToLuckMangoResponseDtos(Mockito.any(List.class))).willReturn(luckMangoResponseDtos);
//        // when
//        ResultActions actions =
//                mockMvc.perform(
//                        get("/luckMango/member?memberId={memberId}&page={page}&size={size}&sort=desc",1L,1,10,memberId,page,size)
//                                .accept(MediaType.APPLICATION_JSON)
//                                .contentType(MediaType.APPLICATION_JSON)
////                                .content(content)
//                );
//
//        // then
//        actions
//                .andExpect(status().isOk())
//                .andDo(document("get-luckMangos",
//                        getRequestPreProcessor(),
//                        getResponsePreProcessor(),
//                        requestParameters(
//                                parameterWithName("memberId").description("??????ID"),
//                                parameterWithName("page").description("?????? ?????????"),
//                                parameterWithName("size").description("???????????? ??????"),
//                                parameterWithName("sort").description("??????")
//                        ),
//                        responseFields(
//                                List.of(
//                                        fieldWithPath("data").type(JsonFieldType.ARRAY).description("?????? ?????????"),
//                                        fieldWithPath("data[].luckMangoId").type(JsonFieldType.NUMBER).description("????????? ?????????"),
//                                        fieldWithPath("data[].title").type(JsonFieldType.STRING).description("??????"),
//                                        fieldWithPath("data[].mangoBody").type(JsonFieldType.STRING).description("??????"),
//                                        fieldWithPath("data[].bgImage").type(JsonFieldType.STRING).description("???????????????"),
//                                        fieldWithPath("data[].bgVideo").type(JsonFieldType.STRING).description("?????????"),
//                                        fieldWithPath("data[].likeCount").type(JsonFieldType.NUMBER).description("????????????"),
//                                        fieldWithPath("data[].createdAt").type(JsonFieldType.STRING).description("????????????"),
//                                        fieldWithPath("data[].modifiedAt").type(JsonFieldType.STRING).description("????????????"),
//                                        fieldWithPath("data[].reveal").type(JsonFieldType.STRING).description("???????????? ??? :'true'??? ????????? ?????????"),
//                                        fieldWithPath("data[].member").type(JsonFieldType.OBJECT).description("?????? ?????????"),
//                                        fieldWithPath("data[].member.memberId").type(JsonFieldType.NUMBER).description("?????? ??????"),
//                                        fieldWithPath("data[].member.name").type(JsonFieldType.STRING).description("?????? ?????????"),
//                                        fieldWithPath("data[].member.email").type(JsonFieldType.STRING).description("?????? ?????????"),
//                                        fieldWithPath("data[].member.imgUrl").type(JsonFieldType.STRING).description("?????? ?????????"),
//                                        fieldWithPath("pageInfo.page").type(JsonFieldType.NUMBER).description("?????? ?????????"),
//                                        fieldWithPath("pageInfo.size").type(JsonFieldType.NUMBER).description("???????????? ??????"),
//                                        fieldWithPath("pageInfo.totalElements").type(JsonFieldType.NUMBER).description("????????? ??? ??????"),
//                                        fieldWithPath("pageInfo.totalPages").type(JsonFieldType.NUMBER).description("??? ????????????")
//                                )
//                        )
//                ));
//    }
//
//
//    @Test
//    public void deleteLuckMangoTest() throws Exception {
//        // given
//        long luckMangoId= 1L;
//
//        // when
//        ResultActions actions =
//                mockMvc.perform(
//                        delete("/luckMango/{luckMango-id}",1L,luckMangoId)
//                                .accept(MediaType.APPLICATION_JSON)
//                                .contentType(MediaType.APPLICATION_JSON));
//        // then
//        MvcResult result = actions.andExpect(status().isNoContent())
//                .andDo(
//                        document("delete-luckMango")).andReturn();
//    }
//}
