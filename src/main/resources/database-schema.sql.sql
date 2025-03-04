create table users (
	id bigint primary key identity(1, 1),
	username varchar(50) not null unique,
	password varchar(MAX) not null,
    created_at_timestamp datetimeoffset not null,
    updated_at_timestamp datetimeoffset
);

create table user_profile_picture_img (
    id bigint primary key identity(1, 1),
    img varbinary(MAX),
    img_type varchar(MAX),
    created_at_timestamp datetimeoffset not null,
);

create table user_profile (
    id bigint primary key identity(1, 1),
    user_id bigint not null foreign key references users(id),
    profile_picture_id bigint foreign key references user_profile_picture(id),
    first_name nvarchar(MAX),
    last_name nvarchar(MAX),
    description nvarchar(MAX),
    created_at_timestamp datetimeoffset not null,
    updated_at_timestamp datetimeoffset
);

create table user_profile_picture (
    id bigint primary key identity(1, 1),
    profile_picture_img_id bigint foreign key references user_profile_picture_img(id),
    profile_picture_url nvarchar(MAX) not null,
    created_at_timestamp datetimeoffset not null,
    updated_at_timestamp datetimeoffset
);

create table user_book_genre_preferences (
    user_profile_id bigint foreign key references user_profile(id),
    book_genre_id bigint foreign key references book_genre(id),
    primary key (user_profile_id, book_genre_id)
);

create table authority (
	id bigint primary key identity(1, 1),
	user_id bigint not null foreign key references users(id),
	authority varchar(50) not null,
    created_at_timestamp datetimeoffset not null,
    updated_at_timestamp datetimeoffset
);

create table book (
    id bigint primary key identity(1, 1),
    external_api_id nvarchar(MAX),
    isbn varchar(20) not null unique,
    title nvarchar(MAX),
    published_year int,
    published_at_timestamp datetimeoffset,
    description nvarchar(MAX),
    language nvarchar(MAX),
    num_of_pages int,
    created_at_timestamp datetimeoffset not null,
    updated_at_timestamp datetimeoffset
);

create table book_genre (
    id bigint primary key identity(1, 1),
    name nvarchar(max) not null,
);

create table book_book_genre (
    book_id bigint foreign key references book(id),
    book_genre_id bigint foreign key references book_genre(id),
    primary key (book_id, book_genre_id)
);

create table book_cover (
    id bigint primary key identity(1, 1),
    book_id bigint not null foreign key references book(id),
    cover_url varchar(MAX),
    created_at_timestamp datetimeoffset not null,
    updated_at_timestamp datetimeoffset
);

create table book_cover_img (
    id bigint primary key identity(1, 1),
    book_cover_id bigint not null foreign key references book_cover(id),
    cover_img varbinary(MAX),
    img_type varchar(MAX),
    created_at_timestamp datetimeoffset not null,
    updated_at_timestamp datetimeoffset
);

create table book_reading_details (
    id bigint primary key identity(1, 1),
    user_id bigint not null foreign key references users(id),
    book_id bigint not null foreign key references book(id),
    current_page int,
    progress_percentage int,
    finished_at_timestamp datetimeoffset,
    created_at_timestamp datetimeoffset not null,
    updated_at_timestamp datetimeoffset
);

create table book_rating (
    id bigint primary key identity(1, 1),
    user_id bigint not null foreign key references users(id),
    book_id bigint not null foreign key references book(id),
    rating float not null,
    rating_justification nvarchar(MAX),
    created_at_timestamp datetimeoffset not null,
    updated_at_timestamp datetimeoffset
);

create table publisher (
    id bigint primary key identity(1, 1),
    name nvarchar(MAX) not null,
    created_at_timestamp datetimeoffset not null,
    updated_at_timestamp datetimeoffset
);

create table book_publisher (
    book_id bigint foreign key references book(id),
    publisher_id bigint foreign key references publisher(id),
    primary key (book_id, publisher_id)
);

create table author (
    id bigint primary key identity(1, 1),
    external_api_id nvarchar(MAX),
    user_id bigint foreign key references users(id),
    full_name nvarchar(MAX),
    description nvarchar(MAX),
    has_account bit not null,
    created_at_timestamp datetimeoffset not null,
    updated_at_timestamp datetimeoffset
);

create table author_photo_img (
    id bigint primary key identity(1, 1),
    img varbinary(MAX) not null,
    img_type varchar(50) not null,
    created_at_timestamp datetimeoffset not null,
    updated_at_timestamp datetimeoffset
);

create table author_photo (
    id bigint primary key identity(1, 1),
    photo_img_id bigint foreign key references author_photo_img(id),
    photo_url varchar(max) not null,
    created_at_timestamp datetimeoffset not null,
    updated_at_timestamp datetimeoffset
)

create table author_rating (
    id bigint primary key identity(1, 1),
    author_id bigint foreign key references author(id),
    user_id bigint foreign key references users(id),
    rating float not null,
    rating_justification nvarchar(MAX),
    created_at_timestamp datetimeoffset not null,
    updated_at_timestamp datetimeoffset
);

create table book_author (
    book_id bigint foreign key references book(id),
    author_id bigint foreign key references author(id),
    primary key (book_id, author_id)
);

create table bookshelf (
    id bigint primary key identity(1, 1),
    user_id bigint not null foreign key references users(id),
    name nvarchar(MAX) not null,
    type nvarchar(MAX) not null,
    description nvarchar(MAX),
    created_at_timestamp datetimeoffset not null,
    updated_at_timestamp datetimeoffset
);

create table bookshelf_book (
    bookshelf_id bigint foreign key references bookshelf(id),
    book_id bigint foreign key references book(id),
    primary key (bookshelf_id, book_id)
);

create table book_recommendation (
    id bigint primary key identity(1, 1),
    user_id bigint not null foreign key references users(id),
    book_id bigint foreign key references book(id),
    book_isbn nvarchar(20),
    recommendation_reason nvarchar(MAX),
    created_at_timestamp datetimeoffset not null,
);

create table book_genre_recommendation (
    id bigint primary key identity(1, 1),
    user_id bigint not null foreign key references users(id),
    book_genre_id bigint not null foreign key references book_genre(id),
    recommendation_reason nvarchar(MAX),
    created_at_timestamp datetimeoffset not null
);

create table tag (
    id bigint primary key identity(1, 1),
    name nvarchar(50) not null unique,
    created_at_timestamp datetimeoffset not null,
    updated_at_timestamp datetimeoffset
);

create table book_tag (
    id bigint primary key identity(1, 1),
    book_id bigint not null foreign key references book(id),
    tag_id bigint not null foreign key references tag(id),
    created_at_timestamp datetimeoffset not null
);

create table author_tag (
    id bigint primary key identity(1, 1),
    author_id bigint not null foreign key references author(id),
    tag_id bigint not null foreign key references tag(id),
    created_at_timestamp datetimeoffset not null
);

create table challenge (
    id bigint primary key identity(1, 1),
    user_id bigint foreign key references users(id),
    description nvarchar(MAX) not null,
    start_at_timestamp datetimeoffset not null,
    finish_at_timestamp datetimeoffset not null,
    created_at_timestamp datetimeoffset not null,
    updated_at_timestamp datetimeoffset
);

create table challenge_participant_details (
    id bigint primary key identity(1, 1),
    challenge_id bigint not null foreign key references challenge(id),
    user_id bigint not null foreign key references users(id),
    status nvarchar(50),
    started_at_timestamp datetimeoffset not null,
    finished_at_at_timestamp datetimeoffset,
    created_at_timestamp datetimeoffset not null,
    updated_at_timestamp datetimeoffset
);

create table notification (
    id bigint primary key identity(1, 1),
    user_id bigint not null foreign key references users(id),
    message nvarchar(MAX) not null,
    is_read bit not null,
    read_at_timestamp datetimeoffset,
    created_at_timestamp datetimeoffset not null,
);