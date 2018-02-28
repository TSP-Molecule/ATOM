package structures.enums;


/**
 *  Elem Enum
 *      @author Emily Anible
 *      Contains information about the elements of the periodic table, easily accessible from anywhere in the program.
 *
 *      To reference, use Elem.[ELEMENT_NAME].get[PROPERTY]() or Elem.get( atomicNumber ).get[Property}().
 */
public enum Elem {
    //  Name           num sym  mass        color    econfig                  eneg   radius bonding            density   group           type                  state
    NULL          (0  ,""  ,-99999     ,0x999999,""                      ,-99999,-99999,""                ,-99999   ,null           ,   null                  ,-1),
    Hydrogen      (1  ,"H" ,1.00794    ,0xFFFFFF,"1s1"                   ,2.2   ,37    ,"diatomic"        ,0.0000899,Group.Group2   ,Type.NONMETAL            ,2)     ,
    Helium        (2  ,"He",4.002602   ,0xD9FFFF,"1s2"                   ,1     ,32    ,"atomic"          ,0.0001785,Group.Group1   ,Type.NOBLE_GAS            ,2)     ,
    Lithium       (3  ,"Li",6.941      ,0xCC80FF,"[He]-2s1"              ,0.98  ,134   ,"metallic"        ,0.535    ,Group.Group2   ,Type.ALKALI_METAL         ,0)     ,
    Beryllium     (4  ,"Be",9.012182   ,0xC2FF00,"[He]-2s2"              ,1.57  ,90    ,"metallic"        ,1.848    ,Group.Group3   ,Type.ALKALINE_EARTH_METAL  ,0)     ,
    Boron         (5  ,"B" ,10.811     ,0xFFB5B5,"[He]-2s2-2p1"          ,2.04  ,82    ,"covalent-network",2.46     ,Group.Group13  ,Type.METALLOID           ,0)     ,
    Carbon        (6  ,"C" ,12.0107    ,0x909090,"[He]-2s2-2p2"          ,2.55  ,77    ,"covalent-network",2.26     ,Group.Group14  ,Type.NONMETAL            ,0)     ,
    Nitrogen      (7  ,"N" ,14.0067    ,0x3050F8,"[He]-2s2-2p3"          ,3.04  ,75    ,"diatomic"        ,0.001251 ,Group.Group15  ,Type.NONMETAL            ,2)     ,
    Oxygen        (8  ,"O" ,15.9994    ,0xFF0D0D,"[He]-2s2-2p4"          ,3.44  ,73    ,"diatomic"        ,0.001429 ,Group.Group16  ,Type.NONMETAL            ,2)     ,
    Fluorine      (9  ,"F" ,18.9984032 ,0x90E050,"[He]-2s2-2p5"          ,3.98  ,71    ,"atomic"          ,0.001696 ,Group.Group17  ,Type.HALOGEN             ,2)     ,
    Neon          (10 ,"Ne",20.1797    ,0xB3E3F5,"[He]-2s2-2p6"          ,-99999,69    ,"atomic"          ,0.0009   ,Group.Group18  ,Type.NOBLE_GAS            ,2)     ,
    Sodium        (11 ,"Na",22.98976928,0xAB5CF2,"[Ne]-3s1"              ,0.93  ,154   ,"metallic"        ,0.968    ,Group.Group1   ,Type.ALKALI_METAL         ,0)     ,
    Magnesium     (12 ,"Mg",24.305     ,0x8AFF00,"[Ne]-3s2"              ,1.31  ,130   ,"metallic"        ,1.738    ,Group.Group2   ,Type.ALKALINE_EARTH_METAL  ,0)     ,
    Aluminum      (13 ,"Al",26.9815386 ,0xBFA6A6,"[Ne]-3s2-3p1"          ,1.61  ,118   ,"metallic"        ,2.7      ,Group.Group13  ,Type.METAL               ,0)     ,
    Silicon       (14 ,"Si",28.0855    ,0xF0C8A0,"[Ne]-3s2-3p2"          ,1.9   ,111   ,"metallic"        ,2.33     ,Group.Group14  ,Type.METALLOID           ,0)     ,
    Phosphorus    (15 ,"P" ,30.973762  ,0xFF8000,"[Ne]-3s2-3p3"          ,2.19  ,106   ,"covalent-network",1.823    ,Group.Group15  ,Type.NONMETAL            ,0)     ,
    Sulfur        (16 ,"S" ,32.065     ,0xFFFF30,"[Ne]-3s2-3p4"          ,2.58  ,102   ,"covalent-network",1.96     ,Group.Group16  ,Type.NONMETAL            ,0)     ,
    Chlorine      (17 ,"Cl",35.453     ,0x1FF01F,"[Ne]-3s2-3p5"          ,3.16  ,99    ,"covalent-network",0.003214 ,Group.Group17  ,Type.HALOGEN             ,2)     ,
    Argon         (18 ,"Ar",39.948     ,0x80D1E3,"[Ne]-3s2-3p6"          ,-99999,97    ,"atomic"          ,0.001784 ,Group.Group18  ,Type.NOBLE_GAS            ,2)     ,
    Potassium     (19 ,"K" ,39.0983    ,0x8F40D4,"[Ar]-4s1"              ,0.82  ,196   ,"metallic"        ,0.856    ,Group.Group1   ,Type.ALKALI_METAL         ,0)     ,
    Calcium       (20 ,"Ca",40.078     ,0x3DFF00,"[Ar]-4s2"              ,1     ,174   ,"metallic"        ,1.55     ,Group.Group2   ,Type.ALKALINE_EARTH_METAL  ,0)     ,
    Scandium      (21 ,"Sc",44.955912  ,0xE6E6E6,"[Ar]-3d1-4s2"          ,1.36  ,144   ,"metallic"        ,2.985    ,Group.Group3   ,Type.TRANSITION_METAL     ,0)     ,
    Titanium      (22 ,"Ti",47.867     ,0xBFC2C7,"[Ar]-3d2-4s2"          ,1.54  ,136   ,"metallic"        ,4.507    ,Group.Group4   ,Type.TRANSITION_METAL     ,0)     ,
    Vanadium      (23 ,"V" ,50.9415    ,0xA6A6AB,"[Ar]-3d3-4s2"          ,1.63  ,125   ,"metallic"        ,6.11     ,Group.Group5   ,Type.TRANSITION_METAL     ,0)     ,
    Chromium      (24 ,"Cr",51.9961    ,0x8A99C7,"[Ar]-3d5-4s1"          ,1.66  ,127   ,"metallic"        ,7.14     ,Group.Group6   ,Type.TRANSITION_METAL     ,0)     ,
    Manganese     (25 ,"Mn",54.938045  ,0x9C7AC7,"[Ar]-3d5-4s2"          ,1.55  ,139   ,"metallic"        ,7.47     ,Group.Group7   ,Type.TRANSITION_METAL     ,0)     ,
    Iron          (26 ,"Fe",55.845     ,0xE06633,"[Ar]-3d6-4s2"          ,1.83  ,125   ,"metallic"        ,7.874    ,Group.Group8   ,Type.TRANSITION_METAL     ,0)     ,
    Cobalt        (27 ,"Co",58.933195  ,0xF090A0,"[Ar]-3d7-4s2"          ,1.88  ,126   ,"metallic"        ,8.9      ,Group.Group9   ,Type.TRANSITION_METAL     ,0)     ,
    Nickel        (28 ,"Ni",58.6934    ,0x50D050,"[Ar]-3d8-4s2"          ,1.91  ,121   ,"metallic"        ,8.908    ,Group.Group10  ,Type.TRANSITION_METAL     ,0)     ,
    Copper        (29 ,"Cu",63.546     ,0xC88033,"[Ar]-3d10-4s1"         ,1.9   ,138   ,"metallic"        ,8.92     ,Group.Group11  ,Type.TRANSITION_METAL     ,0)     ,
    Zinc          (30 ,"Zn",65.38      ,0x7D80B0,"[Ar]-3d10-4s2"         ,1.65  ,131   ,"metallic"        ,7.14     ,Group.Group12  ,Type.TRANSITION_METAL     ,0)     ,
    Gallium       (31 ,"Ga",69.723     ,0xC28F8F,"[Ar]-3d10-4s2-4p1"     ,1.81  ,126   ,"metallic"        ,5.904    ,Group.Group13  ,Type.METAL               ,0)     ,
    Germanium     (32 ,"Ge",72.64      ,0x668F8F,"[Ar]-3d10-4s2-4p2"     ,2.01  ,122   ,"metallic"        ,5.323    ,Group.Group14  ,Type.METALLOID           ,0)     ,
    Arsenic       (33 ,"As",74.9216    ,0xBD80E3,"[Ar]-3d10-4s2-4p3"     ,2.18  ,119   ,"metallic"        ,5.727    ,Group.Group15  ,Type.METALLOID           ,0)     ,
    Selenium      (34 ,"Se",78.96      ,0xFFA100,"[Ar]-3d10-4s2-4p4"     ,2.55  ,116   ,"metallic"        ,4.819    ,Group.Group16  ,Type.NONMETAL            ,0)     ,
    Bromine       (35 ,"Br",79.904     ,0xA62929,"[Ar]-3d10-4s2-4p5"     ,2.96  ,114   ,"covalent-network",3.12     ,Group.Group17  ,Type.HALOGEN             ,1)     ,
    Krypton       (36 ,"Kr",83.798     ,0x5CB8D1,"[Ar]-3d10-4s2-4p6"     ,-99999,110   ,"atomic"          ,0.00375  ,Group.Group18  ,Type.NOBLE_GAS            ,2)     ,
    Rubidium      (37 ,"Rb",85.4678    ,0x702EB0,"[Kr]-5s1"              ,0.82  ,211   ,"metallic"        ,1.532    ,Group.Group1   ,Type.ALKALI_METAL         ,0)     ,
    Strontium     (38 ,"Sr",87.62      ,0x00FF00,"[Kr]-5s2"              ,0.95  ,192   ,"metallic"        ,2.63     ,Group.Group2   ,Type.ALKALINE_EARTH_METAL  ,0)     ,
    Yttrium       (39 ,"Y" ,88.90585   ,0x94FFFF,"[Kr]-4d1-5s2"          ,1.22  ,162   ,"metallic"        ,4.472    ,Group.Group3   ,Type.TRANSITION_METAL     ,0)     ,
    Zirconium     (40 ,"Zr",91.224     ,0x94E0E0,"[Kr]-4d2-5s2"          ,1.33  ,148   ,"metallic"        ,6.511    ,Group.Group4   ,Type.TRANSITION_METAL     ,0)     ,
    Niobium       (41 ,"Nb",92.90638   ,0x73C2C9,"[Kr]-4d4-5s1"          ,1.6   ,137   ,"metallic"        ,8.57     ,Group.Group5   ,Type.TRANSITION_METAL     ,0)     ,
    Molybdenum    (42 ,"Mo",95.96      ,0x54B5B5,"[Kr]-4d5-5s1"          ,2.16  ,145   ,"metallic"        ,10.28    ,Group.Group6   ,Type.TRANSITION_METAL     ,0)     ,
    Technetium    (43 ,"Tc",98         ,0x3B9E9E,"[Kr]-4d5-5s2"          ,1.9   ,156   ,"metallic"        ,11.5     ,Group.Group7   ,Type.TRANSITION_METAL     ,0)     ,
    Ruthenium     (44 ,"Ru",101.07     ,0x248F8F,"[Kr]-4d7-5s1"          ,2.2   ,126   ,"metallic"        ,12.37    ,Group.Group8   ,Type.TRANSITION_METAL     ,0)     ,
    Rhodium       (45 ,"Rh",102.9055   ,0x0A7D8C,"[Kr]-4d8-5s1"          ,2.28  ,135   ,"metallic"        ,12.45    ,Group.Group9   ,Type.TRANSITION_METAL     ,0)     ,
    Palladium     (46 ,"Pd",106.42     ,0x6985  ,"[Kr]-4d10"             ,2.2   ,131   ,"metallic"        ,12.023   ,Group.Group10  ,Type.TRANSITION_METAL     ,0)     ,
    Silver        (47 ,"Ag",107.8682   ,0xC0C0C0,"[Kr]-4d10-5s1"         ,1.93  ,153   ,"metallic"        ,10.49    ,Group.Group11  ,Type.TRANSITION_METAL     ,0)     ,
    Cadmium       (48 ,"Cd",112.411    ,0xFFD98F,"[Kr]-4d10-5s2"         ,1.69  ,148   ,"metallic"        ,8.65     ,Group.Group12  ,Type.TRANSITION_METAL     ,0)     ,
    Indium        (49 ,"In",114.818    ,0xA67573,"[Kr]-4d10-5s2-5p1"     ,1.78  ,144   ,"metallic"        ,7.31     ,Group.Group13  ,Type.METAL               ,0)     ,
    Tin           (50 ,"Sn",118.71     ,0x668080,"[Kr]-4d10-5s2-5p2"     ,1.96  ,141   ,"metallic"        ,7.31     ,Group.Group14  ,Type.METAL               ,0)     ,
    Antimony      (51 ,"Sb",121.76     ,0x9E63B5,"[Kr]-4d10-5s2-5p3"     ,2.05  ,138   ,"metallic"        ,6.697    ,Group.Group15  ,Type.METALLOID           ,0)     ,
    Tellurium     (52 ,"Te",127.6      ,0xD47A00,"[Kr]-4d10-5s2-5p4"     ,2.1   ,135   ,"metallic"        ,6.24     ,Group.Group16  ,Type.METALLOID           ,0)     ,
    Iodine        (53 ,"I" ,126.90447  ,0x940094,"[Kr]-4d10-5s2-5p5"     ,2.66  ,133   ,"covalent-network",4.94     ,Group.Group17  ,Type.HALOGEN             ,0)     ,
    Xenon         (54 ,"Xe",131.293    ,0x429EB0,"[Kr]-4d10-5s2-5p6"     ,-99999,130   ,"atomic"          ,0.0059   ,Group.Group18  ,Type.NOBLE_GAS            ,2)     ,
    Cesium        (55 ,"Cs",132.9054519,0x57178F,"[Xe]-6s1"              ,0.79  ,225   ,"metallic"        ,1.879    ,Group.Group1   ,Type.ALKALI_METAL         ,0)     ,
    Barium        (56 ,"Ba",137.327    ,0x00C900,"[Xe]-6s2"              ,0.89  ,198   ,"metallic"        ,3.51     ,Group.Group2   ,Type.ALKALINE_EARTH_METAL  ,0)     ,
    Lanthanum     (57 ,"La",138.90547  ,0x70D4FF,"[Xe]-5d1-6s2"          ,1.1   ,169   ,"metallic"        ,6.146    ,Group.GroupL   ,Type.LANTHANIDE,0)     ,
    Cerium        (58 ,"Ce",140.116    ,0xFFFFC7,"[Xe]-4f1-5d1-6s2"      ,1.12  ,-99999,"metallic"        ,6.689    ,Group.GroupL   ,Type.LANTHANIDE,0)     ,
    Praseodymium  (59 ,"Pr",140.90765  ,0xD9FFC7,"[Xe]-4f3-6s2"          ,1.13  ,-99999,"metallic"        ,6.64     ,Group.GroupL   ,Type.LANTHANIDE,0)     ,
    Neodymium     (60 ,"Nd",144.242    ,0xC7FFC7,"[Xe]-4f4-6s2"          ,1.14  ,-99999,"metallic"        ,7.01     ,Group.GroupL   ,Type.LANTHANIDE,0)     ,
    Promethium    (61 ,"Pm",145        ,0xA3FFC7,"[Xe]-4f5-6s2"          ,1.13  ,-99999,"metallic"        ,7.264    ,Group.GroupL   ,Type.LANTHANIDE,0)     ,
    Samarium      (62 ,"Sm",150.36     ,0x8FFFC7,"[Xe]-4f6-6s2"          ,1.17  ,-99999,"metallic"        ,7.353    ,Group.GroupL   ,Type.LANTHANIDE,0)     ,
    Europium      (63 ,"Eu",151.964    ,0x61FFC7,"[Xe]-4f7-6s2"          ,1.2   ,-99999,"metallic"        ,5.244    ,Group.GroupL   ,Type.LANTHANIDE,0)     ,
    Gadolinium    (64 ,"Gd",157.25     ,0x45FFC7,"[Xe]-4f7-5d1-6s2"      ,1.2   ,-99999,"metallic"        ,7.901    ,Group.GroupL   ,Type.LANTHANIDE,0)     ,
    Terbium       (65 ,"Tb",158.92535  ,0x30FFC7,"[Xe]-4f9-6s2"          ,1.2   ,-99999,"metallic"        ,8.219    ,Group.GroupL   ,Type.LANTHANIDE,0)     ,
    Dysprosium    (66 ,"Dy",162.5      ,0x1FFFC7,"[Xe]-4f10-6s2"         ,1.22  ,-99999,"metallic"        ,8.551    ,Group.GroupL   ,Type.LANTHANIDE,0)     ,
    Holmium       (67 ,"Ho",164.93032  ,0x00FF9C,"[Xe]-4f11-6s2"         ,1.23  ,-99999,"metallic"        ,8.795    ,Group.GroupL   ,Type.LANTHANIDE,0)     ,
    Erbium        (68 ,"Er",167.259    ,0x000000,"[Xe]-4f12-6s2"         ,1.24  ,-99999,"metallic"        ,9.066    ,Group.GroupL   ,Type.LANTHANIDE,0)     ,
    Thulium       (69 ,"Tm",168.93421  ,0x00D452,"[Xe]-4f13-6s2"         ,1.25  ,-99999,"metallic"        ,9.321    ,Group.GroupL   ,Type.LANTHANIDE,0)     ,
    Ytterbium     (70 ,"Yb",173.054    ,0x00BF38,"[Xe]-4f14-6s2"         ,1.1   ,-99999,"metallic"        ,6.57     ,Group.GroupL   ,Type.LANTHANIDE,0)     ,
    Lutetium      (71 ,"Lu",174.9668   ,0x00AB24,"[Xe]-4f14-5d1-6s2"     ,1.27  ,160   ,"metallic"        ,9.841    ,Group.GroupL   ,Type.LANTHANIDE,0)     ,
    Hafnium       (72 ,"Hf",178.49     ,0x4DC2FF,"[Xe]-4f14-5d2-6s2"     ,1.3   ,150   ,"metallic"        ,13.31    ,Group.Group4   ,Type.TRANSITION_METAL     ,0)     ,
    Tantalum      (73 ,"Ta",180.94788  ,0x4DA6FF,"[Xe]-4f14-5d3-6s2"     ,1.5   ,138   ,"metallic"        ,16.65    ,Group.Group5   ,Type.TRANSITION_METAL     ,0)     ,
    Tungsten      (74 ,"W" ,183.84     ,0x2194D6,"[Xe]-4f14-5d4-6s2"     ,2.36  ,146   ,"metallic"        ,19.25    ,Group.Group6   ,Type.TRANSITION_METAL     ,0)     ,
    Rhenium       (75 ,"Re",186.207    ,0x267DAB,"[Xe]-4f14-5d5-6s2"     ,1.9   ,159   ,"metallic"        ,21.02    ,Group.Group7   ,Type.TRANSITION_METAL     ,0)     ,
    Osmium        (76 ,"Os",190.23     ,0x266696,"[Xe]-4f14-5d6-6s2"     ,2.2   ,128   ,"metallic"        ,22.61    ,Group.Group8   ,Type.TRANSITION_METAL     ,0)     ,
    Iridium       (77 ,"Ir",192.217    ,0x175487,"[Xe]-4f14-5d7-6s2"     ,2.2   ,137   ,"metallic"        ,22.65    ,Group.Group9   ,Type.TRANSITION_METAL     ,0)     ,
    Platinum      (78 ,"Pt",195.084    ,0xD0D0E0,"[Xe]-4f14-5d9-6s1"     ,2.28  ,128   ,"metallic"        ,21.09    ,Group.Group10  ,Type.TRANSITION_METAL     ,0)     ,
    Gold          (79 ,"Au",196.966569 ,0xFFD123,"[Xe]-4f14-5d10-6s1"    ,2.54  ,144   ,"metallic"        ,19.3     ,Group.Group11  ,Type.TRANSITION_METAL     ,0)     ,
    Mercury       (80 ,"Hg",200.59     ,0xB8B8D0,"[Xe]-4f14-5d10-6s2"    ,2     ,149   ,"metallic"        ,13.534   ,Group.Group12  ,Type.TRANSITION_METAL     ,1)     ,
    Thallium      (81 ,"Tl",204.3833   ,0xA6544D,"[Xe]-4f14-5d10-6s2-6p1",2.04  ,148   ,"metallic"        ,11.85    ,Group.Group13  ,Type.METAL               ,0)     ,
    Lead          (82 ,"Pb",207.2      ,0x575961,"[Xe]-4f14-5d10-6s2-6p2",2.33  ,147   ,"metallic"        ,11.34    ,Group.Group14  ,Type.METAL               ,0)     ,
    Bismuth       (83 ,"Bi",208.9804   ,0x9E4FB5,"[Xe]-4f14-5d10-6s2-6p3",2.02  ,146   ,"metallic"        ,9.78     ,Group.Group15  ,Type.METAL               ,0)     ,
    Polonium      (84 ,"Po",209        ,0xAB5C00,"[Xe]-4f14-5d10-6s2-6p4",2     ,-99999,"metallic"        ,9.196    ,Group.Group16  ,Type.METALLOID           ,0)     ,
    Astatine      (85 ,"At",210        ,0x754F45,"[Xe]-4f14-5d10-6s2-6p5",2.2   ,-99999,"covalent-network",-99999   ,Group.Group17  ,Type.HALOGEN             ,0)     ,
    Radon         (86 ,"Rn",222        ,0x428296,"[Xe]-4f14-5d10-6s2-6p6",-99999,145   ,"atomic"          ,0.00973  ,Group.Group18  ,Type.NOBLE_GAS            ,2)     ,
    Francium      (87 ,"Fr",223        ,0x420066,"[Rn]-7s1"              ,0.7   ,-99999,"metallic"        ,-99999   ,Group.Group1   ,Type.ALKALI_METAL         ,0)     ,
    Radium        (88 ,"Ra",226        ,0x007D00,"[Rn]-7s2"              ,0.9   ,-99999,"metallic"        ,5        ,Group.Group2   ,Type.ALKALINE_EARTH_METAL  ,0)     ,
    Actinium      (89 ,"Ac",227        ,0x70ABFA,"[Rn]-6d1-7s2"          ,1.1   ,-99999,"metallic"        ,10.07    ,Group.GroupA   ,Type.ACTINIDE,0)     ,
    Thorium       (90 ,"Th",232.03806  ,0x00BAFF,"[Rn]-6d2-7s2"          ,1.3   ,-99999,"metallic"        ,11.724   ,Group.GroupA   ,Type.ACTINIDE,0)     ,
    Protactinium  (91 ,"Pa",231.03588  ,0x00A1FF,"[Rn]-5f2-6d1-7s2"      ,1.5   ,-99999,"metallic"        ,15.37    ,Group.GroupA   ,Type.ACTINIDE,0)     ,
    Uranium       (92 ,"U" ,238.02891  ,0x008FFF,"[Rn]-5f3-6d1-7s2"      ,1.38  ,-99999,"metallic"        ,19.05    ,Group.GroupA   ,Type.ACTINIDE,0)     ,
    Neptunium     (93 ,"Np",237        ,0x0080FF,"[Rn]-5f4-6d1-7s2"      ,1.36  ,-99999,"metallic"        ,20.45    ,Group.GroupA   ,Type.ACTINIDE,0)     ,
    Plutonium     (94 ,"Pu",244        ,0x006BFF,"[Rn]-5f6-7s2"          ,1.28  ,-99999,"metallic"        ,19.816   ,Group.GroupA   ,Type.ACTINIDE,0)     ,
    Americium     (95 ,"Am",243        ,0x545CF2,"[Rn]-5f7-7s2"          ,1.3   ,-99999,"metallic"        ,-99999   ,Group.GroupA   ,Type.ACTINIDE,0)     ,
    Curium        (96 ,"Cm",247        ,0x785CE3,"[Rn]-5f7-6d1-7s2"      ,1.3   ,-99999,"metallic"        ,13.51    ,Group.GroupA   ,Type.ACTINIDE,0)     ,
    Berkelium     (97 ,"Bk",247        ,0x8A4FE3,"[Rn]-5f9-7s2"          ,1.3   ,-99999,"metallic"        ,14.78    ,Group.GroupA   ,Type.ACTINIDE,0)     ,
    Californium   (98 ,"Cf",251        ,0xA136D4,"[Rn]-5f10-7s2"         ,1.3   ,-99999,"metallic"        ,15.1     ,Group.GroupA   ,Type.ACTINIDE,0)     ,
    Einsteinium   (99 ,"Es",252        ,0xB31FD4,"[Rn]-5f11-7s2"         ,1.3   ,-99999,""                ,-99999   ,Group.GroupA   ,Type.ACTINIDE,0)     ,
    Fermium       (100,"Fm",257        ,0xB31FBA,"[Rn]-5f12-7s2"         ,1.3   ,-99999,""                ,-99999   ,Group.GroupA   ,Type.ACTINIDE,-1)    ,
    Mendelevium   (101,"Md",258        ,0xB30DA6,"[Rn]-5f13-7s2"         ,1.3   ,-99999,""                ,-99999   ,Group.GroupA   ,Type.ACTINIDE,-1)    ,
    Nobelium      (102,"No",259        ,0xBD0D87,"[Rn]-5f14-7s2"         ,1.3   ,-99999,""                ,-99999   ,Group.GroupA   ,Type.ACTINIDE,-1)    ,
    Lawrencium    (103,"Lr",262        ,0xC70066,"[Rn]-5f14-7s2-7p1"     ,1.3   ,-99999,""                ,-99999   ,Group.GroupA   ,Type.ACTINIDE    ,-1)    ,
    Rutherfordium (104,"Rf",267        ,0xCC0059,"[Rn]-5f14-6d2-7s2"     ,-99999,-99999,""                ,-99999   ,Group.Group4   ,Type.TRANSITION_METAL     ,-1)    ,
    Dubnium       (105,"Db",268        ,0xD1004F,"[Rn]-5f14-6d3-7s2"     ,-99999,-99999,""                ,-99999   ,Group.Group5   ,Type.TRANSITION_METAL     ,-1)    ,
    Seaborgium    (106,"Sg",271        ,0xD90045,"[Rn]-5f14-6d4-7s2"     ,-99999,-99999,""                ,-99999   ,Group.Group6   ,Type.TRANSITION_METAL     ,-1)    ,
    Bohrium       (107,"Bh",272        ,0xE00038,"[Rn]-5f14-6d5-7s2"     ,-99999,-99999,""                ,-99999   ,Group.Group7   ,Type.TRANSITION_METAL     ,-1)    ,
    Hassium       (108,"Hs",270        ,0xE6002E,"[Rn]-5f14-6d6-7s2"     ,-99999,-99999,""                ,-99999   ,Group.Group8   ,Type.TRANSITION_METAL     ,-1)    ,
    Meitnerium    (109,"Mt",276        ,0xEB0026,"[Rn]-5f14-6d7-7s2"     ,-99999,-99999,""                ,-99999   ,Group.Group9   ,Type.TRANSITION_METAL     ,-1)    ,
    Darmstadtium  (110,"Ds",281        ,0x000000,"[Rn]-5f14-6d9-7s1"     ,-99999,-99999,""                ,-99999   ,Group.Group10  ,Type.TRANSITION_METAL     ,-1)    ,
    Roentgenium   (111,"Rg",280        ,0x000000,"[Rn]-5f14-6d10-7s1"    ,-99999,-99999,""                ,-99999   ,Group.Group11  ,Type.TRANSITION_METAL     ,-1)    ,
    Copernicium   (112,"Cn",285        ,0x000000,"[Rn]-5f14-6d10-7s2"    ,-99999,-99999,""                ,-99999   ,Group.Group12  ,Type.TRANSITION_METAL     ,-1)    ,
    Nihonium      (113,"Nh",284        ,0x000000,"[Rn]-5f14-6d10-7s2-7p1",-99999,-99999,""                ,-99999   ,Group.Group13  ,Type.POST_TRANSITION_METAL,-1)    ,
    Flerovium     (114,"Fl",289        ,0x000000,"[Rn]-5f14-6d10-7s2-7p2",-99999,-99999,""                ,-99999   ,Group.Group14  ,Type.POST_TRANSITION_METAL,-1)    ,
    Moscovium     (115,"Mc",288        ,0x000000,"[Rn]-5f14-6d10-7s2-7p3",-99999,-99999,""                ,-99999   ,Group.Group15  ,Type.POST_TRANSITION_METAL,-1)    ,
    Livermorium   (116,"Lv",293        ,0x000000,"[Rn]-5f14-6d10-7s2-7p4",-99999,-99999,""                ,-99999   ,Group.Group16  ,Type.POST_TRANSITION_METAL,-1)    ,
    Tennessine    (117,"Ts",294        ,0x000000,"[Rn]-5f14-6d10-7s2-7p5",-99999,-99999,""                ,-99999   ,Group.Group17  ,Type.POST_TRANSITION_METAL,-1)      ,
    Oganesson     (118,"Og",294        ,0x000000,"[Rn]-5f14-6d10-7s2-7p6",-99999,-99999,""                ,-99999   ,Group.Group18  ,Type.NOBLE_GAS            ,-1);

    private int atomicNumber;
    private String symbol;
    private final double atomicMass;
    private final int color;
    private final String eConfigString;        //Electron Configuration
    private final double eNegativity;
    private final int atomicRadius;
    private final String bondingType;
    private final double density;
    private final Group group;
    private final Type type;
    private final int state;          //0 1 2, solid liquid gas.

    Elem(int atomicNumber, String symbol, double atomicMass, int color, String eConfigString, double eNegativity, int atomicRadius, String bondingType, double density, Group group, Type type, int state) {
        this.atomicNumber = atomicNumber;
        this.symbol = symbol;
        this.atomicMass = atomicMass;
        this.color = color;
        this.eConfigString = eConfigString;
        this.eNegativity = eNegativity;
        this.atomicRadius = atomicRadius;
        this.bondingType = bondingType;
        this.density = density;
        this.group = group;
        this.type = type;
        this.state = state;
    }

    public static Elem get(int atomicNumber) {
        for( Elem e: Elem.values()) {
            if (e.getNum() == atomicNumber) return e;
        }
        return null;
    }

    public static Elem getBySymbol( String symbol) {
        for ( Elem e: Elem.values()) {
            if (e.getSymbol().equalsIgnoreCase(symbol)) return e;
        }
        return null;
    }

    public String getName() {
        return name();
    }

    public int getNum() {
        return atomicNumber;
    }

    public String getSymbol() {
        return symbol;
    }


    public double getAtomicMass() {
        return atomicMass;
    }

    public int getColor() {
        return color;
    }

    public String geteConfigString() {
        return eConfigString;
    }

    public double geteNegativity() {
        return eNegativity;
    }

    public int getAtomicRadius() {
        return atomicRadius;
    }

    public String getBondingType() {
        return bondingType;
    }

    public double getDensity() {
        return density;
    }

    public Type getType() {
        return type;
    }

    public int getState() {
        return state;
    }

    @Override
    public String toString() {
        return "Elem{" +
                "atomicNumber=" + atomicNumber +
                ", symbol='" + symbol + '\'' +
                ", atomicMass=" + atomicMass +
                ", color=" + color +
                ", eConfigString='" + eConfigString + '\'' +
                ", eNegativity=" + eNegativity +
                ", atomicRadius=" + atomicRadius +
                ", bondingType='" + bondingType + '\'' +
                ", density=" + density +
                ", type='" + type + '\'' +
                ", state=" + state +
                '}';
    }

    public Group getGroup() {
        return group;
    }
}
